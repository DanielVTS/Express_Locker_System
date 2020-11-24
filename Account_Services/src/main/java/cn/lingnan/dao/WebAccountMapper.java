package cn.lingnan.dao;

import cn.lingnan.dto.WebAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WebAccount record);

    int insertSelective(WebAccount record);

    WebAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WebAccount record);

    int updateByPrimaryKey(WebAccount record);
}