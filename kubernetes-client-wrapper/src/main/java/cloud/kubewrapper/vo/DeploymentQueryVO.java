package cloud.kubewrapper.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class DeploymentQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private Long strategy;

}
