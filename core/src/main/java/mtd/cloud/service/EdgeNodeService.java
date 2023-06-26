package mtd.cloud.service;

import mtd.cloud.dto.EdgeNodeDTO;
import mtd.cloud.entity.EdgeNode;
import mtd.cloud.repository.EdgeNodeRepository;
import mtd.cloud.vo.EdgeNodeQueryVO;
import mtd.cloud.vo.EdgeNodeUpdateVO;
import mtd.cloud.vo.EdgeNodeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EdgeNodeService {

    @Autowired
    private EdgeNodeRepository edgeNodeRepository;

    public Long save(EdgeNodeVO vO) {
        EdgeNode bean = new EdgeNode();
        BeanUtils.copyProperties(vO, bean);
        bean = edgeNodeRepository.save(bean);
        return bean.getId();
    }

    public void delete(Long id) {
        edgeNodeRepository.deleteById(id);
    }

    public void update(Long id, EdgeNodeUpdateVO vO) {
        EdgeNode bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        edgeNodeRepository.save(bean);
    }

    public EdgeNodeDTO getById(Long id) {
        EdgeNode original = requireOne(id);
        return toDTO(original);
    }

    public Page<EdgeNodeDTO> query(EdgeNodeQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private EdgeNodeDTO toDTO(EdgeNode original) {
        EdgeNodeDTO bean = new EdgeNodeDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private EdgeNode requireOne(Long id) {
        return edgeNodeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public List<EdgeNodeDTO> findAll() {
        List<EdgeNodeDTO> list = new ArrayList<>();
        edgeNodeRepository.findAll().forEach(node -> list.add(toDTO(node)));
        return list;
    }
}
