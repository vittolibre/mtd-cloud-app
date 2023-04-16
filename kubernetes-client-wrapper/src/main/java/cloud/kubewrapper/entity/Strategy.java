package cloud.kubewrapper.entity;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "strategy", schema = "cloud")
public class Strategy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "type", nullable = false)
    private String type;

}
