package mtd.cloud.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class DeploymentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "name can not null")
    private String name;

    @NotNull(message = "strategy can not null")
    private String strategy;

    private String namespace;

}
