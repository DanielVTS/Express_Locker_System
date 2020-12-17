package cn.lingnan.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PackageInformation {
    private Long packageId;

    private Long expressNumber;

    private Integer expressCompany;

    private String receiverPhone;

    private Integer expOperatorId;

    private String expOperatorPhone;

    private Date createTime;

    private Date updateTime;

    private Date statusTime;

    private Integer status;

    private String remark;
}