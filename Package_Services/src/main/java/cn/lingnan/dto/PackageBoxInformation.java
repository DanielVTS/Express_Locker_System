package cn.lingnan.dto;

import java.util.Date;
import lombok.Data;

@Data
public class PackageBoxInformation {
    private Object pbiId;

    private Integer packageId;

    private Integer lockerId;

    private Object lockerBoxId;

    private String packageCode;

    private String operatorId;

    private Date createTime;

    private Date updateTime;

    private Date statusTime;

    private Integer status;

    private String remark;
}