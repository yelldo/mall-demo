package com.mmall.dao;

import com.mmall.pojo.MaterialCategoryRelation;

public interface MaterialCategoryRelationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MaterialCategoryRelation record);

    int insertSelective(MaterialCategoryRelation record);

    MaterialCategoryRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MaterialCategoryRelation record);

    int updateByPrimaryKey(MaterialCategoryRelation record);
}