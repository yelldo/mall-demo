package com.mmall.dao;

import com.mmall.pojo.PictureMaterial2;

public interface PictureMaterial2Mapper {
    int deleteByPrimaryKey(Long id);

    int insert(PictureMaterial2 record);

    int insertSelective(PictureMaterial2 record);

    PictureMaterial2 selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PictureMaterial2 record);

    int updateByPrimaryKey(PictureMaterial2 record);
}