package cn.lingnan.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.lingnan.dao.LockerBoxInformationMapper;
import cn.lingnan.dto.LockerBoxInformation;
import cn.lingnan.service.LockerBoxInformationService;
@Service
public class LockerBoxInformationServiceImpl implements LockerBoxInformationService{

    @Resource
    private LockerBoxInformationMapper lockerBoxInformationMapper;

    @Override
    public int deleteByPrimaryKey(Object lockerBoxId) {
        return lockerBoxInformationMapper.deleteByPrimaryKey(lockerBoxId);
    }

    @Override
    public int insert(LockerBoxInformation record) {
        return lockerBoxInformationMapper.insert(record);
    }

    @Override
    public int insertSelective(LockerBoxInformation record) {
        return lockerBoxInformationMapper.insertSelective(record);
    }

    @Override
    public LockerBoxInformation selectByPrimaryKey(Object lockerBoxId) {
        return lockerBoxInformationMapper.selectByPrimaryKey(lockerBoxId);
    }

    @Override
    public int updateByPrimaryKeySelective(LockerBoxInformation record) {
        return lockerBoxInformationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(LockerBoxInformation record) {
        return lockerBoxInformationMapper.updateByPrimaryKey(record);
    }

}
