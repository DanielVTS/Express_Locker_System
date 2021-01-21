package cn.lingnan.dao;

import cn.lingnan.dto.WebAccount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WebAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WebAccount record);

    int insertSelective(WebAccount record);

    WebAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WebAccount record);

    int updateByPrimaryKey(WebAccount record);

    List<WebAccount> findByPhone(String phone);

    List<WebAccount> findByName(String name);
}