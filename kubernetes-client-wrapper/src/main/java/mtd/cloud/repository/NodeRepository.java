package mtd.cloud.repository;

import mtd.cloud.entity.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface NodeRepository extends JpaRepository<Node, String>, JpaSpecificationExecutor<Node> {

    @Query(nativeQuery = true, value = "SELECT *\n" +
            "FROM cloud.node\n" +
            "ORDER BY random()\n" +
            "LIMIT 1;")
    Node findRandomNode();

}