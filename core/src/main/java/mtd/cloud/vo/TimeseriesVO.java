package mtd.cloud.vo;

import lombok.Data;

import java.util.Date;


@Data
public class TimeseriesVO {
    private String idGateway;

    private String idDevice;

    private String deviceAttribute;

    private String deviceAttributeValue;

    private String deviceAttributeType;

    private Date timestamp;

}
