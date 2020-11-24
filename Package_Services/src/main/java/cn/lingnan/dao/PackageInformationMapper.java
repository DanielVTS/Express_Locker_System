package cn.lingnan.dao;

import cn.lingnan.dto.PackageInformation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PackageInformationMapper {
    int deleteByPrimaryKey(Integer packageId);

    int insert(PackageInformation record);

    int insertSelective(PackageInformation record);

    PackageInformation selectByPrimaryKey(Integer packageId);

    int updateByPrimaryKeySelective(PackageInformation record);

    int updateByPrimaryKey(PackageInformation record);
}