package mtd.cloud.service;

import mtd.cloud.dto.ParameterDTO;
import mtd.cloud.entity.Parameter;
import mtd.cloud.repository.ParameterRepository;
import mtd.cloud.vo.ParameterQueryVO;
import mtd.cloud.vo.ParameterUpdateVO;
import mtd.cloud.vo.ParameterVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ParameterService {

    @Autowired
    private ParameterRepository parameterRepository;

    public Long save(ParameterVO vO) {
        Parameter bean = new Parameter();
        BeanUtils.copyProperties(vO, bean);
        bean = parameterRepository.save(bean);
        return bean.getId();
    }

    public void delete(Long id) {
        parameterRepository.deleteById(id);
    }

    public void update(ParameterUpdateVO vO) {
        Parameter bean = requireOne(vO.getId());
        BeanUtils.copyProperties(vO, bean);
        parameterRepository.save(bean);
    }

    public ParameterDTO getById(Long id) {
        Parameter original = requireOne(id);
        return toDTO(original);
    }

    public Page<ParameterDTO> query(ParameterQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ParameterDTO toDTO(Parameter original) {
        ParameterDTO bean = new ParameterDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Parameter requireOne(Long id) {
        return parameterRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
