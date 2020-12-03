package cn.lingnan.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LockerBasicInformation {
    private Long lockerId;

    private String province;

    private String city;

    private Integer totalBox;

    private Integer usedBox;

    private Date createTime;

    private Date updateTime;

    private Date statusTime;

    private Integer rowNum;

    private Integer columnNum;

    private String remark;
}