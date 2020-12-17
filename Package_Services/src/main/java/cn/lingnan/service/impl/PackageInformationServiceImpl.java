package cn.lingnan.service.impl;

import cn.lingnan.dao.PackageInformationMapper;
import cn.lingnan.dto.PackageInformation;
import cn.lingnan.service.PackageInformationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PackageInformationServiceImpl implements PackageInformationService {

    @Resource
    private PackageInformationMapper packageInformationMapper;

    @Override
    public int deleteByPrimaryKey(Long packageId) {
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
    public PackageInformation selectByPrimaryKey(Long packageId) {
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

    @Override
    public List<PackageInformation> findByExpressNumber(Long expressNumber) {
        return packageInformationMapper.findByExpressNumber(expressNumber);
    }
}
