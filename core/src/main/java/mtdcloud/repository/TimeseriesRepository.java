package mtdcloud.repository;

import mtdcloud.entity.Timeseries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
public interface TimeseriesRepository extends JpaRepository<Timeseries, String>, JpaSpecificationExecutor<Timeseries> {

    Timeseries findTopByDeviceAttributeOrderByTimestampDesc(String attributeValue);
}