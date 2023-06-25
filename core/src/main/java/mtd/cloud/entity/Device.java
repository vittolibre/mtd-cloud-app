package mtd.cloud.entity;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "device", schema = "cloud")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

    @Column(name = "edge_node", nullable = false)
    private Long edgeNode;

}
