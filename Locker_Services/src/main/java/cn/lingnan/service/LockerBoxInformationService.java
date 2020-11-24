package cn.lingnan.service;

import cn.lingnan.dto.LockerBoxInformation;
public interface LockerBoxInformationService{


    int deleteByPrimaryKey(Object lockerBoxId);

    int insert(LockerBoxInformation record);

    int insertSelective(LockerBoxInformation record);

    LockerBoxInformation selectByPrimaryKey(Object lockerBoxId);

    int updateByPrimaryKeySelective(LockerBoxInformation record);

    int updateByPrimaryKey(LockerBoxInformation record);

}
