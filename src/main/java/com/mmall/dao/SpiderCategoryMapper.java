package com.mmall.dao;

import com.mmall.pojo.SpiderCategory;

public interface SpiderCategoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SpiderCategory record);

    int insertSelective(SpiderCategory record);

    SpiderCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpiderCategory record);

    int updateByPrimaryKey(SpiderCategory record);
}