package mtdcloud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "timeseries", schema = "cloud")
public class Timeseries {

    @Column(name = "id_gateway", nullable = false)
    private String idGateway;

    @Column(name = "id_device", nullable = false)
    private String idDevice;

    @Column(name = "device_attribute", nullable = false)
    private String deviceAttribute;

    @Column(name = "device_attribute_value")
    private String deviceAttributeValue;

    @Column(name = "device_attribute_type", nullable = false)
    private String deviceAttributeType;

    @Id
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

}
