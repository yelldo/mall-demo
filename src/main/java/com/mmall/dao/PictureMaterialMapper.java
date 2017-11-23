package com.mmall.dao;

import com.mmall.pojo.PictureMaterial;

public interface PictureMaterialMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PictureMaterial record);

    int insertSelective(PictureMaterial record);

    PictureMaterial selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PictureMaterial record);

    int updateByPrimaryKey(PictureMaterial record);
}