package mtd.cloud.entity;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "edge_node", schema = "cloud")
public class EdgeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "hostname", nullable = false)
    private String hostname;

    @Column(name = "ipaddress", nullable = false)
    private String ipaddress;

    @Column(name = "label")
    private String label;

}
