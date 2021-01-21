package cn.lingnan.service.impl;

import cn.lingnan.dao.WebAccountMapper;
import cn.lingnan.dto.WebAccount;
import cn.lingnan.service.WebAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WebAccountServiceImpl implements WebAccountService {

    @Resource
    private WebAccountMapper webAccountMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return webAccountMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(WebAccount record) {
        return webAccountMapper.insert(record);
    }

    @Override
    public int insertSelective(WebAccount record) {
        return webAccountMapper.insertSelective(record);
    }

    @Override
    public WebAccount selectByPrimaryKey(Integer id) {
        return webAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(WebAccount record) {
        return webAccountMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(WebAccount record) {
        return webAccountMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<WebAccount> findByPhone(String phone) {
        return webAccountMapper.findByPhone(phone);
    }

    @Override
    public List<WebAccount> findByName(String name) {
        return webAccountMapper.findByName(name);
    }
}
