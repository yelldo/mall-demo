package com.mmall.pojo.spider;

import com.mmall.pojo.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by luzy on 2017/11/21.
 */
public class ClothesPicture extends BaseBean {

    private String title;

    // 来源时间，一般是文章或图片的发布时间
    private Date sdate;

    // 描述性的内容，或者存储文章也可以
    private String desc;

    // 图片url
    private String url;

    // 来源url
    private String surl;

    // 标签，分类用
    private List<Long> lables;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public List<Long> getLables() {
        return lables;
    }

    public void setLables(List<Long> lables) {
        this.lables = lables;
    }
}
