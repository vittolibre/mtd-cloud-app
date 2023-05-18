package mtd.cloud.repository;

import mtd.cloud.entity.Timeseries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TimeseriesRepository extends JpaRepository<Timeseries, String>, JpaSpecificationExecutor<Timeseries> {

    Timeseries findTopByDeviceAttributeOrderByTimestampDesc(String attributeValue);
}