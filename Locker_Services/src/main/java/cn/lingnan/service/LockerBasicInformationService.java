package cn.lingnan.service;

import cn.lingnan.dto.LockerBasicInformation;
public interface LockerBasicInformationService{


    int deleteByPrimaryKey(Integer lockerId);

    int insert(LockerBasicInformation record);

    int insertSelective(LockerBasicInformation record);

    LockerBasicInformation selectByPrimaryKey(Integer lockerId);

    int updateByPrimaryKeySelective(LockerBasicInformation record);

    int updateByPrimaryKey(LockerBasicInformation record);

}
