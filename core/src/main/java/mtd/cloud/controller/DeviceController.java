package mtd.cloud.controller;

import mtd.cloud.dto.DeviceDTO;
import mtd.cloud.service.DeviceService;
import mtd.cloud.vo.DeviceQueryVO;
import mtd.cloud.vo.DeviceUpdateVO;
import mtd.cloud.vo.DeviceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    public String save(@Valid @RequestBody DeviceVO vO) {
        return deviceService.save(vO).toString();
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @NotNull @PathVariable("id") Long id) {
        deviceService.delete(id);
    }

    @PutMapping("/{id}")
    public void update(@Valid @NotNull @PathVariable("id") Long id,
                       @Valid @RequestBody DeviceUpdateVO vO) {
        deviceService.update(id, vO);
    }

    @GetMapping("/{id}")
    public DeviceDTO getById(@Valid @NotNull @PathVariable("id") Long id) {
        return deviceService.getById(id);
    }

    @GetMapping
    public Page<DeviceDTO> query(@Valid DeviceQueryVO vO) {
        return deviceService.query(vO);
    }
}
