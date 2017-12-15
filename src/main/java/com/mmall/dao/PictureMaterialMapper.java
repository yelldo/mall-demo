package com.mmall.dao;

import com.mmall.pojo.PictureMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PictureMaterialMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PictureMaterial record);

    int insertSelective(PictureMaterial record);

    PictureMaterial selectByPrimaryKey(Long id);

    List<PictureMaterial> selectByLimit(@Param("start") Long start, @Param("limit") Integer limit);

    int updateByPrimaryKeySelective(PictureMaterial record);

    int updateByPrimaryKey(PictureMaterial record);

    /**
     * 批量更新
     * @param list
     * @return
     */
    int updateBatchCaseWhen(List<PictureMaterial> list);

    long selectCount();
}