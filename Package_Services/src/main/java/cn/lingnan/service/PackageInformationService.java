package cn.lingnan.service;

import cn.lingnan.dto.PackageInformation;

import java.util.List;

public interface PackageInformationService {


    int deleteByPrimaryKey(Long packageId);

    int insert(PackageInformation record);

    int insertSelective(PackageInformation record);

    PackageInformation selectByPrimaryKey(Long packageId);

    int updateByPrimaryKeySelective(PackageInformation record);

    int updateByPrimaryKey(PackageInformation record);

    List<PackageInformation> findByExpressNumber(Long expressNumber);

}
