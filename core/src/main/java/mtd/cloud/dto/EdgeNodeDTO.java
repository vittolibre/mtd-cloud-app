package mtd.cloud.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class EdgeNodeDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private String hostname;

    private String ipaddress;

    private String label;

}
