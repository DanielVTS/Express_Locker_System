package cn.lingnan.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.lingnan.dao.PackageInformationMapper;
import cn.lingnan.dto.PackageInformation;
import cn.lingnan.service.PackageInformationService;
@Service
public class PackageInformationServiceImpl implements PackageInformationService{

    @Resource
    private PackageInformationMapper packageInformationMapper;

    @Override
    public int deleteByPrimaryKey(Integer packageId) {
        return packageInformationMapper.deleteByPrimaryKey(packageId);
    }

    @Override
    public int insert(PackageInformation record) {
        return packageInformationMapper.insert(record);
    }

    @Override
    public int insertSelective(PackageInformation record) {
        return packageInformationMapper.insertSelective(record);
    }

    @Override
    public PackageInformation selectByPrimaryKey(Integer packageId) {
        return packageInformationMapper.selectByPrimaryKey(packageId);
    }

    @Override
    public int updateByPrimaryKeySelective(PackageInformation record) {
        return packageInformationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PackageInformation record) {
        return packageInformationMapper.updateByPrimaryKey(record);
    }

}
