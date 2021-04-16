package cn.lingnan.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class PackageBoxInformation {
    private Object pbiId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long packageId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long lockerId;

    private Object lockerBoxId;

    private String packageCode;

    private String operatorId;

    private Date createTime;

    private Date updateTime;

    private Date statusTime;

    private Integer status;

    private String remark;
}