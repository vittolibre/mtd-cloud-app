package mtd.cloud.service;

import io.fabric8.kubernetes.api.model.ServiceAccount;
import io.fabric8.kubernetes.api.model.ServiceAccountBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import jakarta.persistence.EntityNotFoundException;
import mtd.cloud.entity.Deployment;
import mtd.cloud.entity.Node;
import mtd.cloud.entity.NodeLabel;
import mtd.cloud.entity.Strategy;
import mtd.cloud.repository.*;
import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.PatchUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
//@Component
public class MtdStrategyService implements ApplicationRunner {

    public static final String IP_SHUFFLING_CONTAINER = "ip-shuffling-container";
    public static final String SERVICE_ACCOUNT_SHUFFLING_CONTAINER = "service-account-shuffling-container";
    @Autowired
    private DeploymentRepository deploymentRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private NodeLabelRepository nodeLabelRepository;
    @Autowired
    private StrategyRepository strategyRepository;

    @Autowired
    private ParameterRepository parameterRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        KubernetesClient kubernetesClient = new KubernetesClientBuilder().build();
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        AppsV1Api appsV1Api = new AppsV1Api(client);

        List<Deployment> deployments = deploymentRepository.findAll();

        while (true) {

            Strategy ipShuffling = strategyRepository.findByName(IP_SHUFFLING_CONTAINER).orElseThrow(EntityNotFoundException::new);
            Strategy saShuffling = strategyRepository.findByName(SERVICE_ACCOUNT_SHUFFLING_CONTAINER).orElseThrow(EntityNotFoundException::new);

            for (Deployment deployment : deployments) {
                V1Deployment runningDeployment =
                        appsV1Api.readNamespacedDeployment(deployment.getName(), deployment.getNamespace(), null);

                if(Boolean.TRUE == ipShuffling.getEnabled()){
                    restartDeployment(runningDeployment);
                    changeDeploymentNode(runningDeployment);
                }
                if(Boolean.TRUE == saShuffling.getEnabled()){
                    changeServiceAccount(kubernetesClient, deployment, runningDeployment);
                }

                String deploymentJson = client.getJSON().serialize(runningDeployment);

                PatchUtils.patch(
                        V1Deployment.class,
                        () ->
                                appsV1Api.patchNamespacedDeploymentCall(
                                        deployment.getName(),
                                        deployment.getNamespace(),
                                        new V1Patch(deploymentJson),
                                        null,
                                        null,
                                        "kubectl-rollout",
                                        null,
                                        null,
                                        null),
                        V1Patch.PATCH_FORMAT_STRATEGIC_MERGE_PATCH,
                        client);
                log.info("Riavvio eseguito per il deployment {}", runningDeployment.getMetadata().getName());
            }

            parameterRepository.findByKey("").orElseThrow(EntityNotFoundException::new);
            Thread.sleep(60000);
        }
    }

    private static void changeServiceAccount(KubernetesClient kubernetesClient, Deployment deployment, V1Deployment runningDeployment) throws InterruptedException {
        String generatedString = RandomStringUtils.randomAlphanumeric(5).toLowerCase();
        String serviceAccountName = deployment.getName().replace("-", ".") + "." + generatedString;
        ServiceAccount sa = new ServiceAccountBuilder().withNewMetadata().withName(serviceAccountName).endMetadata().build();
        kubernetesClient.serviceAccounts().resource(sa).createOrReplace();

        Thread.sleep(1000);

        String oldServiceAccountName = runningDeployment.getSpec().getTemplate().getSpec().getServiceAccountName();
        runningDeployment.getSpec().getTemplate().getSpec().setServiceAccountName(serviceAccountName);
        ServiceAccount old = new ServiceAccountBuilder().withNewMetadata().withName(oldServiceAccountName).endMetadata().build();
        kubernetesClient.serviceAccounts().resource(old).delete();
    }

    private void changeDeploymentNode(V1Deployment runningDeployment) {
        Map<String, String> map = runningDeployment.getSpec().getTemplate().getSpec().getNodeSelector();
        Node node = nodeRepository.findRandomNode();
        NodeLabel nameLabel = nodeLabelRepository.findByIdNodeAndKey(node.getId(), "name");
        if (map == null) {
            map = new HashMap<>();
        }
        map.remove("name");
        map.put("name", nameLabel.getValue());
        runningDeployment.getSpec()
                .getTemplate()
                .getSpec()
                .setNodeSelector(map);
    }

    private static void restartDeployment(V1Deployment runningDeployment) {
        runningDeployment
                .getSpec()
                .getTemplate()
                .getMetadata()
                .putAnnotationsItem("kubectl.kubernetes.io/restartedAt", LocalDateTime.now().toString());
    }
}
