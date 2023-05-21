package mtd.cloud.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class ParameterDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private String key;

    private String value;

}
