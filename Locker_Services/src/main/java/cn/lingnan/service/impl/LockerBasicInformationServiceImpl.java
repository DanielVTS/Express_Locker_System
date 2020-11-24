package cn.lingnan.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.lingnan.dao.LockerBasicInformationMapper;
import cn.lingnan.dto.LockerBasicInformation;
import cn.lingnan.service.LockerBasicInformationService;
@Service
public class LockerBasicInformationServiceImpl implements LockerBasicInformationService{

    @Resource
    private LockerBasicInformationMapper lockerBasicInformationMapper;

    @Override
    public int deleteByPrimaryKey(Integer lockerId) {
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
    public LockerBasicInformation selectByPrimaryKey(Integer lockerId) {
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

}
