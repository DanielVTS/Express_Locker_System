package cn.lingnan.dao;

import cn.lingnan.dto.LockerBoxInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LockerBoxInformationMapper {
    int deleteByPrimaryKey(Object lockerBoxId);

    int insert(LockerBoxInformation record);

    int insertSelective(LockerBoxInformation record);

    LockerBoxInformation selectByPrimaryKey(Object lockerBoxId);

    int updateByPrimaryKeySelective(LockerBoxInformation record);

    int updateByPrimaryKey(LockerBoxInformation record);

    List<LockerBoxInformation> findBoxListInOneLocker(Long lockerId);
}