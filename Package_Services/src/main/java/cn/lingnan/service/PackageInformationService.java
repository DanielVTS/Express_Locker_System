package cn.lingnan.service;

import cn.lingnan.dto.PackageInformation;
import cn.lingnan.util.PageResult;

import java.util.List;

public interface PackageInformationService {


    int deleteByPrimaryKey(Long packageId);

    int insert(PackageInformation record);

    int insertSelective(PackageInformation record);

    PackageInformation selectByPrimaryKey(Long packageId);

    int updateByPrimaryKeySelective(PackageInformation record);

    int updateByPrimaryKey(PackageInformation record);

    List<PackageInformation> findByExpressNumber(String expressNumber);

    PageResult<PackageInformation> findPackageByPage(String query, Integer pagenum, Integer pagesize);
}
