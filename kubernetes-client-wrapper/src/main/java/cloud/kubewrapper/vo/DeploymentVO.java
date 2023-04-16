package cloud.kubewrapper.vo;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


@Data
public class DeploymentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String name;

    @NotNull
    private Long strategy;

}
