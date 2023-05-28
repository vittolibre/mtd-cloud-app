package mtd.cloud.controller;

import mtd.cloud.dto.ParameterDTO;
import mtd.cloud.service.ParameterService;
import mtd.cloud.vo.ParameterQueryVO;
import mtd.cloud.vo.ParameterUpdateVO;
import mtd.cloud.vo.ParameterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/parameter")
public class ParameterController {

    @Autowired
    private ParameterService parameterService;

    @PutMapping
    public void update(@Valid @RequestBody ParameterUpdateVO vO) {
        parameterService.update(vO);
    }

    @GetMapping("/{id}")
    public ParameterDTO getById(@Valid @NotNull @PathVariable("id") Long id) {
        return parameterService.getById(id);
    }

    @GetMapping
    public Page<ParameterDTO> query(@Valid ParameterQueryVO vO) {
        return parameterService.query(vO);
    }

    @GetMapping("/all")
    public List<ParameterDTO> findAll() {
        return parameterService.findAll();
    }
}
