package mtd.cloud.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class DeviceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private Long id;

    @NotNull(message = "identifier can not null")
    private String identifier;

    private String label;

    private String description;

    @NotNull(message = "edgeNode can not null")
    private Long edgeNode;

}
