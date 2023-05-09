package cloud.kubewrapper.repository;

import cloud.kubewrapper.entity.NodeLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NodeLabelRepository extends JpaRepository<NodeLabel, Integer>, JpaSpecificationExecutor<NodeLabel> {

    NodeLabel findByIdNodeAndKey(Long id, String key);
}