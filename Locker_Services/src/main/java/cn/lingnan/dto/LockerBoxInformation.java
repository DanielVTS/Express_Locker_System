package cn.lingnan.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class LockerBoxInformation {
    private Object lockerBoxId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long lockerId;

    private Integer lockerColumn;

    private Integer lockerRow;

    private Integer doorStatus;

    private Integer boxIsEmpty;

    private Integer boxType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Date statusTime;

    private String remark;
}