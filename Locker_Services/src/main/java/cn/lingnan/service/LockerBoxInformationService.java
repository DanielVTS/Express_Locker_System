package cn.lingnan.service;

import cn.lingnan.dto.LockerBoxInformation;

import java.util.List;

public interface LockerBoxInformationService {


    int deleteByPrimaryKey(Object lockerBoxId);

    int insert(LockerBoxInformation record);

    int insertSelective(LockerBoxInformation record);

    LockerBoxInformation selectByPrimaryKey(Object lockerBoxId);

    int updateByPrimaryKeySelective(LockerBoxInformation record);

    int updateByPrimaryKey(LockerBoxInformation record);

    List<LockerBoxInformation> findBoxListInOneLocker(Long lockerId);

}
