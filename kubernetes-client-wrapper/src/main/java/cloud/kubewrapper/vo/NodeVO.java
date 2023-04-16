package cloud.kubewrapper.vo;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;


@Data
public class NodeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private String hostname;

    @NotNull
    private String ipAddress;

    private String label;

}
