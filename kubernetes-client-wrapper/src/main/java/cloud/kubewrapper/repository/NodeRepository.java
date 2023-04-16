package cloud.kubewrapper.repository;

import cloud.kubewrapper.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface NodeRepository extends JpaRepository<Node, String>, JpaSpecificationExecutor<Node> {

}