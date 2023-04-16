package cloud.kubewrapper.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = false)
public class DeploymentUpdateVO extends DeploymentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public DeploymentUpdateVO(@NotNull String name, @NotNull Long strategy) {
        super(name, strategy);
    }
}
