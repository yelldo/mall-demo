package com.mmall.pojo;

import java.util.Date;

public class MaterialCategoryRelation {
    private Long id;

    private Long materialId;

    private Long spiderCategoryId;

    private Date createTime;

    private Date updateTime;

    public MaterialCategoryRelation(Long id, Long materialId, Long spiderCategoryId, Date createTime, Date updateTime) {
        this.id = id;
        this.materialId = materialId;
        this.spiderCategoryId = spiderCategoryId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public MaterialCategoryRelation() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Long getSpiderCategoryId() {
        return spiderCategoryId;
    }

    public void setSpiderCategoryId(Long spiderCategoryId) {
        this.spiderCategoryId = spiderCategoryId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}