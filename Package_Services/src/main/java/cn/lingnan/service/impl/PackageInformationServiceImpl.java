package cn.lingnan.service.impl;

import cn.lingnan.dao.PackageInformationMapper;
import cn.lingnan.dto.PackageInformation;
import cn.lingnan.service.PackageInformationService;
import cn.lingnan.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public List<PackageInformation> findByExpressNumber(String expressNumber){
        return packageInformationMapper.findByExpressNumber(expressNumber);
    }


    @Override
    public PageResult<PackageInformation> findPackageByPage(String query, Integer pagenum, Integer pagesize){
        PageHelper.startPage(pagenum, pagesize);
        if(query==null) {
            query="%%";
        }else{
            query="%"+query+"%";
        }
        PageInfo<PackageInformation> pageInfo=new PageInfo<>(packageInformationMapper.findPackageByPage(query));
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }
}
