package mtd.cloud.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceUpdateVO extends DeviceVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
