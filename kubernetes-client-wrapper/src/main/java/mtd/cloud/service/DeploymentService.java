package mtd.cloud.service;

import mtd.cloud.dto.DeploymentDTO;
import mtd.cloud.entity.Deployment;
import mtd.cloud.repository.DeploymentRepository;
import mtd.cloud.vo.DeploymentQueryVO;
import mtd.cloud.vo.DeploymentUpdateVO;
import mtd.cloud.vo.DeploymentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DeploymentService {

    @Autowired
    private DeploymentRepository deploymentRepository;

    public String save(DeploymentVO vO) {
        Deployment bean = new Deployment();
        BeanUtils.copyProperties(vO, bean);
        bean = deploymentRepository.save(bean);
        return bean.getName();
    }

    public void delete(String id) {
        deploymentRepository.deleteById(id);
    }

    public void update(String id, DeploymentUpdateVO vO) {
        Deployment bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        deploymentRepository.save(bean);
    }

    public DeploymentDTO getById(String id) {
        Deployment original = requireOne(id);
        return toDTO(original);
    }

    public Page<DeploymentDTO> query(DeploymentQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private DeploymentDTO toDTO(Deployment original) {
        DeploymentDTO bean = new DeploymentDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Deployment requireOne(String id) {
        return deploymentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public List<DeploymentDTO> findAll() {
        List<DeploymentDTO> list = new ArrayList<>();
        deploymentRepository.findAll().forEach(depl -> list.add(toDTO(depl)));
        return list;
    }
}
