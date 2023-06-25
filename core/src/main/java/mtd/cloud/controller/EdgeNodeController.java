package mtd.cloud.controller;

import mtd.cloud.dto.EdgeNodeDTO;
import mtd.cloud.service.EdgeNodeService;
import mtd.cloud.vo.EdgeNodeQueryVO;
import mtd.cloud.vo.EdgeNodeUpdateVO;
import mtd.cloud.vo.EdgeNodeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/edgeNode")
public class EdgeNodeController {

    @Autowired
    private EdgeNodeService edgeNodeService;

    @PostMapping
    public String save(@Valid @RequestBody EdgeNodeVO vO) {
        return edgeNodeService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Long id) {
        edgeNodeService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Long id,
                       @Valid @RequestBody EdgeNodeUpdateVO vO) {
        edgeNodeService.update(id, vO);
    }

    @GetMapping("/{id}")
    public EdgeNodeDTO getById(@Valid @NotNull @PathVariable("id") Long id) {
        return edgeNodeService.getById(id);
    }

    @GetMapping
    public Page<EdgeNodeDTO> query(@Valid EdgeNodeQueryVO vO) {
        return edgeNodeService.query(vO);
    }
}
