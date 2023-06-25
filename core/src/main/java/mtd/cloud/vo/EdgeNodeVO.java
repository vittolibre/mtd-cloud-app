package mtd.cloud.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
public class EdgeNodeVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "id can not null")
    private Long id;

    @NotNull(message = "hostname can not null")
    private String hostname;

    @NotNull(message = "ipaddress can not null")
    private String ipaddress;

    private String label;

}
