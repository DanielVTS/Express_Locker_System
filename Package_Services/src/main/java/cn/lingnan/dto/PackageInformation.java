package cn.lingnan.dto;

import java.util.Date;
import lombok.Data;

@Data
public class PackageInformation {
    private Integer packageId;

    private Integer expressNumber;

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