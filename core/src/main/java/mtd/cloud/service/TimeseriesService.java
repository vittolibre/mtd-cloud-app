package mtd.cloud.service;

import mtd.cloud.dto.TimeseriesDTO;
import mtd.cloud.entity.Timeseries;
import mtd.cloud.repository.TimeseriesRepository;
import mtd.cloud.vo.TimeseriesQueryVO;
import mtd.cloud.vo.TimeseriesUpdateVO;
import mtd.cloud.vo.TimeseriesVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TimeseriesService {

    @Autowired
    private TimeseriesRepository timeseriesRepository;

    public String save(TimeseriesVO vO) {
        Timeseries bean = new Timeseries();
        BeanUtils.copyProperties(vO, bean);
        bean = timeseriesRepository.save(bean);
        return bean.getEdgeHostname();
    }

    public void delete(String id) {
        timeseriesRepository.deleteById(id);
    }

    public void update(String id, TimeseriesUpdateVO vO) {
        Timeseries bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        timeseriesRepository.save(bean);
    }

    public TimeseriesDTO getById(String id) {
        Timeseries original = requireOne(id);
        return toDTO(original);
    }

    public Page<TimeseriesDTO> query(TimeseriesQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private TimeseriesDTO toDTO(Timeseries original) {
        TimeseriesDTO bean = new TimeseriesDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Timeseries requireOne(String id) {
        return timeseriesRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
