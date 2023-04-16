package cloud.kubewrapper.service;

import cloud.kubewrapper.entity.Deployment;
import cloud.kubewrapper.repository.DeploymentRepository;
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
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class StrategyService {

    @Autowired
    private DeploymentRepository deploymentRepository;
    @SneakyThrows
    @PostConstruct
    public void mtdStartup() {

        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        AppsV1Api appsV1Api = new AppsV1Api(client);
        String namespace = "default";

        List<Deployment> deployments = deploymentRepository.findAll();

        while (true) {

            for (Deployment deployment : deployments) {
                V1Deployment runningDeployment =
                        appsV1Api.readNamespacedDeployment(deployment.getName(), namespace, null);


                // Explicitly set "restartedAt" annotation with current date/time to trigger rollout when patch
                // is applied
                runningDeployment
                        .getSpec()
                        .getTemplate()
                        .getMetadata()
                        .putAnnotationsItem("kubectl.kubernetes.io/restartedAt", LocalDateTime.now().toString());

                String deploymentJson = client.getJSON().serialize(runningDeployment);

                PatchUtils.patch(
                        V1Deployment.class,
                        () ->
                                appsV1Api.patchNamespacedDeploymentCall(
                                        deployment.getName(),
                                        namespace,
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
