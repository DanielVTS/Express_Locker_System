package cn.lingnan.service.impl;

import cn.lingnan.dao.PackageBoxInformationMapper;
import cn.lingnan.dto.PackageBoxInformation;
import cn.lingnan.service.PackageBoxInformationService;
import cn.lingnan.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PackageBoxInformationServiceImpl implements PackageBoxInformationService {

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

    @Override
    public PackageBoxInformation findByPackageId(Long packageId) {
        return packageBoxInformationMapper.findByPackageId(packageId);
    }

    @Override
    public PackageBoxInformation findByCode(String code) {
        return packageBoxInformationMapper.findByCode(code);
    }

    @Override
    public PageResult<PackageBoxInformation> findByPage(String query, Integer pagenum, Integer pagesize) {
        PageHelper.startPage(pagenum, pagesize);
        if (query == null) {
            query = "%%";
        } else {
            query = "%" + query + "%";
        }
        PageInfo<PackageBoxInformation> pageInfo = new PageInfo<>(packageBoxInformationMapper.findByPage(query));
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }
}
