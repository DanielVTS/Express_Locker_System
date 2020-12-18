package cn.lingnan.service;

import cn.lingnan.dto.PackageBoxInformation;
public interface PackageBoxInformationService{


    int deleteByPrimaryKey(Object pbiId);

    int insert(PackageBoxInformation record);

    int insertSelective(PackageBoxInformation record);

    PackageBoxInformation selectByPrimaryKey(Object pbiId);

    int updateByPrimaryKeySelective(PackageBoxInformation record);

    int updateByPrimaryKey(PackageBoxInformation record);

    PackageBoxInformation findByPackageId(Long packageId);

}
