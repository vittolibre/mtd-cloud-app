package mtd.cloud.service;

import mtd.cloud.dto.DeviceDTO;
import mtd.cloud.entity.Device;
import mtd.cloud.repository.DeviceRepository;
import mtd.cloud.vo.DeviceQueryVO;
import mtd.cloud.vo.DeviceUpdateVO;
import mtd.cloud.vo.DeviceVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public Long save(DeviceVO vO) {
        Device bean = new Device();
        BeanUtils.copyProperties(vO, bean);
        bean = deviceRepository.save(bean);
        return bean.getId();
    }

    public void delete(Long id) {
        deviceRepository.deleteById(id);
    }

    public void update(Long id, DeviceUpdateVO vO) {
        Device bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        deviceRepository.save(bean);
    }

    public DeviceDTO getById(Long id) {
        Device original = requireOne(id);
        return toDTO(original);
    }

    public Page<DeviceDTO> query(DeviceQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private DeviceDTO toDTO(Device original) {
        DeviceDTO bean = new DeviceDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private Device requireOne(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }

    public List<DeviceDTO> findAll() {
        List<DeviceDTO> list = new ArrayList<>();
        deviceRepository.findAll().forEach(device -> list.add(toDTO(device)));
        return list;
    }
}
