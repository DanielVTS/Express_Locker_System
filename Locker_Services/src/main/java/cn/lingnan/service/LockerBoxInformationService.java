package cn.lingnan.service;

import cn.lingnan.dto.LockerBoxInformation;
import cn.lingnan.util.PageResult;

import java.util.List;

public interface LockerBoxInformationService {


    int deleteByPrimaryKey(Object lockerBoxId);

    int deleteByLockerId(Long lockerId);

    int insert(LockerBoxInformation record);

    int insertSelective(LockerBoxInformation record);

    LockerBoxInformation selectByPrimaryKey(Object lockerBoxId);

    int updateByPrimaryKeySelective(LockerBoxInformation record);

    int updateByPrimaryKey(LockerBoxInformation record);

    List<LockerBoxInformation> findBoxListInOneLocker(Long lockerId);

    PageResult<LockerBoxInformation> findBoxByPage(String query, Integer pageNum, Integer pageSize);

    LockerBoxInformation findBoxForPost(Long lockerId, Integer boxType);


}
