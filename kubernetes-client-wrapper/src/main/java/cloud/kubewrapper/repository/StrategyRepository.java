package cloud.kubewrapper.repository;

import cloud.kubewrapper.entity.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface StrategyRepository extends JpaRepository<Strategy, String>, JpaSpecificationExecutor<Strategy> {

}