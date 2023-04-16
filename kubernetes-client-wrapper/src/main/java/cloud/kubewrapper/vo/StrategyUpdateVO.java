package cloud.kubewrapper.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = false)
public class StrategyUpdateVO extends StrategyVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public StrategyUpdateVO(@NotNull String name, @NotNull Boolean enabled, @NotNull String type) {
        super(name, enabled, type);
    }
}
