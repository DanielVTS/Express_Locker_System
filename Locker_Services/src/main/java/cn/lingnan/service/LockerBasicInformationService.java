package cn.lingnan.service;

import cn.lingnan.dto.LockerBasicInformation;

import java.util.List;

public interface LockerBasicInformationService {


    int deleteByPrimaryKey(Long lockerId);

    int insert(LockerBasicInformation record);

    int insertSelective(LockerBasicInformation record);

    LockerBasicInformation selectByPrimaryKey(Long lockerId);

    int updateByPrimaryKeySelective(LockerBasicInformation record);

    int updateByPrimaryKey(LockerBasicInformation record);

    List<LockerBasicInformation> selectAll();


}
