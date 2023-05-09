package cloud.kubewrapper.service;

import cloud.kubewrapper.dto.NodeLabelDTO;
import cloud.kubewrapper.entity.NodeLabel;
import cloud.kubewrapper.repository.NodeLabelRepository;
import cloud.kubewrapper.vo.NodeLabelQueryVO;
import cloud.kubewrapper.vo.NodeLabelUpdateVO;
import cloud.kubewrapper.vo.NodeLabelVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class NodeLabelService {

    @Autowired
    private NodeLabelRepository nodeLabelRepository;

    public Integer save(NodeLabelVO vO) {
        NodeLabel bean = new NodeLabel();
        BeanUtils.copyProperties(vO, bean);
        bean = nodeLabelRepository.save(bean);
        return bean.getId();
    }

    public void delete(Integer id) {
        nodeLabelRepository.deleteById(id);
    }

    public void update(Integer id, NodeLabelUpdateVO vO) {
        NodeLabel bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        nodeLabelRepository.save(bean);
    }

    public NodeLabelDTO getById(Integer id) {
        NodeLabel original = requireOne(id);
        return toDTO(original);
    }

    public Page<NodeLabelDTO> query(NodeLabelQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private NodeLabelDTO toDTO(NodeLabel original) {
        NodeLabelDTO bean = new NodeLabelDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private NodeLabel requireOne(Integer id) {
        return nodeLabelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
