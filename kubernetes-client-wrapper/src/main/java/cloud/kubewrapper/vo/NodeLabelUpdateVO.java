package cloud.kubewrapper.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class NodeLabelUpdateVO extends NodeLabelVO implements Serializable {
    private static final long serialVersionUID = 1L;

}
