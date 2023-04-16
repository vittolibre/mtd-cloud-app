package cloud.kubewrapper.entity;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "node", schema = "cloud")
public class Node implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "hostname", nullable = false)
    private String hostname;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "label")
    private String label;

}
