package cn.lingnan.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.lingnan.dto.PackageBoxInformation;
import cn.lingnan.dao.PackageBoxInformationMapper;
import cn.lingnan.service.PackageBoxInformationService;
@Service
public class PackageBoxInformationServiceImpl implements PackageBoxInformationService{

    @Resource
    private PackageBoxInformationMapper packageBoxInformationMapper;

    @Override
    public int deleteByPrimaryKey(Object pbiId) {
        return packageBoxInformationMapper.deleteByPrimaryKey(pbiId);
    }

    @Override
    public int insert(PackageBoxInformation record) {
        return packageBoxInformationMapper.insert(record);
    }

    @Override
    public int insertSelective(PackageBoxInformation record) {
        return packageBoxInformationMapper.insertSelective(record);
    }

    @Override
    public PackageBoxInformation selectByPrimaryKey(Object pbiId) {
        return packageBoxInformationMapper.selectByPrimaryKey(pbiId);
    }

    @Override
    public int updateByPrimaryKeySelective(PackageBoxInformation record) {
        return packageBoxInformationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PackageBoxInformation record) {
        return packageBoxInformationMapper.updateByPrimaryKey(record);
    }

}
