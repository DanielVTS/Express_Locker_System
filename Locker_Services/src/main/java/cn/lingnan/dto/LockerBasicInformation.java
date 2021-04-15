package cn.lingnan.dto;

import java.util.Date;
import lombok.Data;

@Data
public class LockerBasicInformation {
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