package cn.lingnan.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class LockerBasicInformation {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long lockerId;

    private String province;

    private String city;

    private String lockerName;

    private Integer totalBox;

    private Integer usedBox;

    private Date createTime;

    private Date updateTime;

    private Date statusTime;

    private Integer rowNum;

    private Integer columnNum;

    private String remark;
}