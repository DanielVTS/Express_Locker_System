package cn.lingnan.service;

import cn.lingnan.dto.PackageInformation;
public interface PackageInformationService{


    int deleteByPrimaryKey(Integer packageId);

    int insert(PackageInformation record);

    int insertSelective(PackageInformation record);

    PackageInformation selectByPrimaryKey(Integer packageId);

    int updateByPrimaryKeySelective(PackageInformation record);

    int updateByPrimaryKey(PackageInformation record);

}
