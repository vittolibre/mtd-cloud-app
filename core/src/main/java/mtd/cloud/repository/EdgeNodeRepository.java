package mtd.cloud.repository;

import mtd.cloud.entity.EdgeNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EdgeNodeRepository extends JpaRepository<EdgeNode, Long>, JpaSpecificationExecutor<EdgeNode> {

}