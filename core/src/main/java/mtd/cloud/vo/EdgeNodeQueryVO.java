package mtd.cloud.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class EdgeNodeQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String hostname;

    private String ipaddress;

    private String label;

}
