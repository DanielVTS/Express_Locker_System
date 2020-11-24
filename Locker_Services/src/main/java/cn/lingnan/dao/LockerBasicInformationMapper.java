package cn.lingnan.dao;

import cn.lingnan.dto.LockerBasicInformation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LockerBasicInformationMapper {
    int deleteByPrimaryKey(Integer lockerId);

    int insert(LockerBasicInformation record);

    int insertSelective(LockerBasicInformation record);

    LockerBasicInformation selectByPrimaryKey(Integer lockerId);

    int updateByPrimaryKeySelective(LockerBasicInformation record);

    int updateByPrimaryKey(LockerBasicInformation record);
}