package mtd.cloud.repository;

import mtd.cloud.entity.Deployment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeploymentRepository extends JpaRepository<Deployment, String>, JpaSpecificationExecutor<Deployment> {

    Deployment findByName(String name);
}