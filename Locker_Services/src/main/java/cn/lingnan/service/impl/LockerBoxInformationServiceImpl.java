package cn.lingnan.service.impl;

import cn.lingnan.dao.LockerBoxInformationMapper;
import cn.lingnan.dto.LockerBoxInformation;
import cn.lingnan.service.LockerBoxInformationService;
import cn.lingnan.util.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LockerBoxInformationServiceImpl implements LockerBoxInformationService {

    @Resource
    private LockerBoxInformationMapper lockerBoxInformationMapper;

    @Override
    public int deleteByPrimaryKey(Object lockerBoxId) {
        return lockerBoxInformationMapper.deleteByPrimaryKey(lockerBoxId);
    }

    @Override
    public int deleteByLockerId(Long lockerId) {
        return lockerBoxInformationMapper.deleteByLockerId(lockerId);
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

    @Override
    public List<LockerBoxInformation> findBoxListInOneLocker(Long lockerId) {
        return lockerBoxInformationMapper.findBoxListInOneLocker(lockerId);
    }

    @Override
    public PageResult<LockerBoxInformation> findBoxByPage(String query, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (query == null) {
            query = "%%";
        } else {
            query = "%" + query + "%";
        }
        PageInfo<LockerBoxInformation> pageInfo = new PageInfo<>(lockerBoxInformationMapper.findBoxList(query));
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getList());
    }
}
