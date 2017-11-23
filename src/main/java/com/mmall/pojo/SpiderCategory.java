package com.mmall.pojo;

import java.util.Date;

public class SpiderCategory {
    private Long id;

    private Long parentId;

    private Date createTime;

    private Date updateTime;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private String description;

    public SpiderCategory(Long id, Long parentId, Date createTime, Date updateTime, String name, Boolean status, Integer sortOrder, String description) {
        this.id = id;
        this.parentId = parentId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.name = name;
        this.status = status;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    public SpiderCategory() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}