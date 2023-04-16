package cloud.kubewrapper.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = false)
public class NodeUpdateVO extends NodeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    public NodeUpdateVO(@NotNull String hostname, @NotNull String ipAddress) {
        super(hostname, ipAddress);
    }
}
