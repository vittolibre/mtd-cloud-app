package mtd.cloud.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String identifier;

    private String label;

    private String description;

    private Long edgeNode;

}
