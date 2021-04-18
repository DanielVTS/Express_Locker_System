package cn.lingnan.dao;

import cn.lingnan.dto.LockerBasicInformation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LockerBasicInformationMapper {
    int deleteByPrimaryKey(Long lockerId);

    int insert(LockerBasicInformation record);

    int insertSelective(LockerBasicInformation record);

    LockerBasicInformation selectByPrimaryKey(Long lockerId);

    int updateByPrimaryKeySelective(LockerBasicInformation record);

    int updateByPrimaryKey(LockerBasicInformation record);

    List<LockerBasicInformation> selectAll(String query);

    LockerBasicInformation findLockerByParam(@Param("province") String province, @Param("city") String city, @Param("name") String name, @Param("total_box") int total_box);

}