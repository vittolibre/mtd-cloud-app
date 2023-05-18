package mtd.cloud.repository;

import mtd.cloud.entity.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StrategyRepository extends JpaRepository<Strategy, String>, JpaSpecificationExecutor<Strategy> {

}