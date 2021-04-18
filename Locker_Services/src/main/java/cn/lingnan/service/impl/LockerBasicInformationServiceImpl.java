package cn.lingnan.service.impl;

import cn.lingnan.dao.LockerBasicInformationMapper;
import cn.lingnan.dto.LockerBasicInformation;
import cn.lingnan.service.LockerBasicInformationService;
import cn.lingnan.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LockerBasicInformationServiceImpl implements LockerBasicInformationService {

    @Resource
    private LockerBasicInformationMapper lockerBasicInformationMapper;

    @Override
    public int deleteByPrimaryKey(Long lockerId) {
        return lockerBasicInformationMapper.deleteByPrimaryKey(lockerId);
    }

    @Override
    public int insert(LockerBasicInformation record) {
        return lockerBasicInformationMapper.insert(record);
    }

    @Override
    public int insertSelective(LockerBasicInformation record) {
        return lockerBasicInformationMapper.insertSelective(record);
    }

    @Override
    public LockerBasicInformation selectByPrimaryKey(Long lockerId) {
        return lockerBasicInformationMapper.selectByPrimaryKey(lockerId);
    }

    @Override
    public int updateByPrimaryKeySelective(LockerBasicInformation record) {
        return lockerBasicInformationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(LockerBasicInformation record) {
        return lockerBasicInformationMapper.updateByPrimaryKey(record);
    }

    @Override
    public LockerBasicInformation findLockerByParam(String province, String city, String name, int total_box) {
        return lockerBasicInformationMapper.findLockerByParam(province, city, name, total_box);
    }

    @Override
    public PageResult<LockerBasicInformation> findLockerByPage(String query, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (query == null) {
            query = "%%";
        } else {
            query = "%" + query + "%";
        }
        PageInfo<LockerBasicInformation> pageInfo = new PageInfo<>(lockerBasicInformationMapper.selectAll(query));
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());

    }

}
