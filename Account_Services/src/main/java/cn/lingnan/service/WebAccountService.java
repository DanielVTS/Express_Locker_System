package cn.lingnan.service;

import cn.lingnan.dto.WebAccount;

import java.util.List;

public interface WebAccountService {


    int deleteByPrimaryKey(Integer id);

    int insert(WebAccount record);

    int insertSelective(WebAccount record);

    WebAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WebAccount record);

    int updateByPrimaryKey(WebAccount record);

    List<WebAccount> findByPhone(String phone);

    List<WebAccount> findByName(String name);

}
