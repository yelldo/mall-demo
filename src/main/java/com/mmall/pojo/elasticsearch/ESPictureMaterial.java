package com.mmall.pojo.elasticsearch;

import lombok.Data;

import java.util.Date;

/**
 * Created by luzy on 2017/12/18.
 */
@Data
public class ESPictureMaterial {

    private Long id;

    private Date createTime;

    private Date updateTime;

    private String title;

    // 用来存json串吧，内容形式可以存的比较多,没有限制
    // {attr1:value1,attr2:value2,...,desc:desc,width:xx,height:xx}
    private String description;

    private String imgUri;

    private String imgUriOrigin;

    private String sourceUrl;
}
