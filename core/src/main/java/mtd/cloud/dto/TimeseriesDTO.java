package mtd.cloud.dto;


import lombok.Data;

import java.util.Date;

@Data
public class TimeseriesDTO {
    private String idGateway;

    private String idDevice;

    private String deviceAttribute;

    private String deviceAttributeValue;

    private String deviceAttributeType;

    private Date timestamp;

}
