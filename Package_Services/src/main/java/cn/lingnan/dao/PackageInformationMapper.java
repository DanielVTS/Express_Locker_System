package cn.lingnan.dao;

import cn.lingnan.dto.PackageInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PackageInformationMapper {
    int deleteByPrimaryKey(Long packageId);

    int insert(PackageInformation record);

    int insertSelective(PackageInformation record);

    PackageInformation selectByPrimaryKey(Long packageId);

    int updateByPrimaryKeySelective(PackageInformation record);

    int updateByPrimaryKey(PackageInformation record);

    List<PackageInformation> findByExpressNumber(Long expressNumber);
}