package mtd.cloud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "strategy", schema = "cloud")
public class Strategy implements Serializable {

    private static final long serialVersionUID = 1L;


    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "enabled", nullable = false)
    private Boolean enabled;


    @Column(name = "scheduling", nullable = false)
    private String scheduling;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

}
