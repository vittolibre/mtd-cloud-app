package mtd.cloud.service;

import mtd.cloud.entity.Deployment;
import mtd.cloud.entity.Node;
import mtd.cloud.entity.NodeLabel;
import mtd.cloud.repository.DeploymentRepository;
import mtd.cloud.repository.NodeLabelRepository;
import mtd.cloud.repository.NodeRepository;
import io.kubernetes.client.custom.V1Patch;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.PatchUtils;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class StrategyService {

    @Autowired
    private DeploymentRepository deploymentRepository;
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private NodeLabelRepository nodeLabelRepository;
    @SneakyThrows
    @PostConstruct
    public void mtdStartup() {

        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        AppsV1Api appsV1Api = new AppsV1Api(client);

        List<Deployment> deployments = deploymentRepository.findAll();

        while (true) {

            for (Deployment deployment : deployments) {
                V1Deployment runningDeployment =
                        appsV1Api.readNamespacedDeployment(deployment.getName(), deployment.getNamespace(), null);

                // Explicitly set "restartedAt" annotation with current date/time to trigger rollout when patch
                // is applied
                runningDeployment
                        .getSpec()
                        .getTemplate()
                        .getMetadata()
                        .putAnnotationsItem("kubectl.kubernetes.io/restartedAt", LocalDateTime.now().toString());

                Map<String, String> map = runningDeployment.getSpec().getTemplate().getSpec().getNodeSelector();

                Node node = nodeRepository.findRandomNode();
                NodeLabel nameLabel  = nodeLabelRepository.findByIdNodeAndKey(node.getId(), "name");

                if(map == null) {
                    map = new HashMap<>();
                }

                map.remove("name");
                map.put("name", nameLabel.getValue());
                runningDeployment.getSpec()
                        .getTemplate()
                        .getSpec()
                        .setNodeSelector(map);

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
                log.info("riavvio eseguito...");
            }

            Thread.sleep(60000);
        }
    }
}
