package cn.lingnan.service;

import cn.lingnan.dto.WebAccount;
import cn.lingnan.util.PageResult;

import java.util.List;

public interface WebAccountService {


    int deleteByPrimaryKey(Long id);

    int insert(WebAccount record);

    int insertSelective(WebAccount record);

    WebAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WebAccount record);

    int updateByPrimaryKey(WebAccount record);

    List<WebAccount> findByPhone(String phone);

    List<WebAccount> findByName(String name);

    PageResult<WebAccount> findUserByPage(String query, Integer pagenum, Integer pagesize);
}
