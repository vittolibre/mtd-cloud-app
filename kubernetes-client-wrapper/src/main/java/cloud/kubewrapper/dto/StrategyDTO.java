package cloud.kubewrapper.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class StrategyDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;

    private Boolean enabled;

    private String scheduling;

}
