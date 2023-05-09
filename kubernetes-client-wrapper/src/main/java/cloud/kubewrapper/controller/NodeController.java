package cloud.kubewrapper.controller;

import cloud.kubewrapper.dto.NodeDTO;
import cloud.kubewrapper.service.NodeService;
import cloud.kubewrapper.vo.NodeQueryVO;
import cloud.kubewrapper.vo.NodeUpdateVO;
import cloud.kubewrapper.vo.NodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @PostMapping
    public String save(@Valid @RequestBody NodeVO vO) {
        return nodeService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Long id) {
        nodeService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Long id,
                       @Valid @RequestBody NodeUpdateVO vO) {
        nodeService.update(id, vO);
    }

    @GetMapping("/{id}")
    public NodeDTO getById(@Valid @NotNull @PathVariable("id") Long id) {
        return nodeService.getById(id);
    }

    @GetMapping
    public Page<NodeDTO> query(@Valid NodeQueryVO vO) {
        return nodeService.query(vO);
    }
}
