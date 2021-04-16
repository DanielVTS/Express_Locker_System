package cn.lingnan.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class PackageInformation {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long packageId;

    private String expressNumber;

    private Integer expressCompany;

    private String receiverPhone;

    private String receiverAddress;

    private String receiverName;

    private String senderName;

    private String senderPhone;

    private String senderAddress;

    private Integer expOperatorId;

    private String expOperatorPhone;

    private Date createTime;

    private Date updateTime;

    private Date statusTime;

    private Integer status;

    private String remark;
}