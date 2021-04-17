package cn.lingnan.dao;

import cn.lingnan.dto.PackageBoxInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PackageBoxInformationMapper {
    int deleteByPrimaryKey(Object pbiId);

    int insert(PackageBoxInformation record);

    int insertSelective(PackageBoxInformation record);

    PackageBoxInformation selectByPrimaryKey(Object pbiId);

    int updateByPrimaryKeySelective(PackageBoxInformation record);

    int updateByPrimaryKey(PackageBoxInformation record);

    PackageBoxInformation findByPackageId(Long packageId);

    PackageBoxInformation findByCode(String code);

    List<PackageBoxInformation> findByPage(String packageCode);

}