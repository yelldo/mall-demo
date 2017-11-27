package com.mmall.pojo;

import java.util.Date;

public class PictureMaterial2 {
    private Long id;

    private Date createTime;

    private Date updateTime;

    private String title;

    private String description;

    private String imgUri;

    private String imgUriOrigin;

    private String sourceUrl;

    public PictureMaterial2(Long id, Date createTime, Date updateTime, String title, String description, String imgUri, String imgUriOrigin, String sourceUrl) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.title = title;
        this.description = description;
        this.imgUri = imgUri;
        this.imgUriOrigin = imgUriOrigin;
        this.sourceUrl = sourceUrl;
    }

    public PictureMaterial2() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri == null ? null : imgUri.trim();
    }

    public String getImgUriOrigin() {
        return imgUriOrigin;
    }

    public void setImgUriOrigin(String imgUriOrigin) {
        this.imgUriOrigin = imgUriOrigin == null ? null : imgUriOrigin.trim();
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl == null ? null : sourceUrl.trim();
    }
}