package cn.lingnan.service;

import cn.lingnan.dto.PackageBoxInformation;
import cn.lingnan.util.PageResult;

public interface PackageBoxInformationService {


    int deleteByPrimaryKey(Object pbiId);

    int insert(PackageBoxInformation record);

    int insertSelective(PackageBoxInformation record);

    PackageBoxInformation selectByPrimaryKey(Object pbiId);

    int updateByPrimaryKeySelective(PackageBoxInformation record);

    int updateByPrimaryKey(PackageBoxInformation record);

    PackageBoxInformation findByPackageId(Long packageId);

    PackageBoxInformation findByCode(String code);

    PageResult<PackageBoxInformation> findByPage(String query, Integer pagenum, Integer pagesize);


}
