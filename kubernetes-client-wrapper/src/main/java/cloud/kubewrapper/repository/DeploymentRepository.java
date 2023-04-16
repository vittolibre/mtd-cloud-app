package cloud.kubewrapper.repository;

import cloud.kubewrapper.entity.Deployment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
public interface DeploymentRepository extends JpaRepository<Deployment, String>, JpaSpecificationExecutor<Deployment> {

    Deployment findByName(String name);
}