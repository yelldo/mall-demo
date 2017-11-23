package com.mmall.spider.processor;

import com.mmall.dao.PictureMaterialMapper;
import com.mmall.pojo.PictureMaterial;
import com.mmall.util.SpringContextUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * url返回json的例子
 *
 * 豆瓣 - search - 穿着
 * Created by luzy on 2017/11/20.
 */
public class DouBanChuanZhuo implements PageProcessor {

    // new 出来的对象，spring不会注入对象
    //@Resource
    //private PictureMaterialMapper pictureMaterialMapper;
    private PictureMaterialMapper pictureMaterialMapper = (PictureMaterialMapper) SpringContextUtil.getBean("pictureMaterialMapper");

    private long pageIndex = 0;
    private long limit = 20;

    private Site site = Site.me().setDomain("douban.com").setSleepTime(3000).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    @Override
    public void process(Page page) {
        /*{
            src: "https://img3.doubanio.com/view/photo/photo/public/p2165236624.webp",
                    author: "丽尔瓦",
                url: "https://www.douban.com/link2/?url=http%3A%2F%2Fwww.douban.com%2Fphotos%2Fphoto%2F2165236624%2F&query=%E7%A9%BF%E7%9D%80&cat_id=1025&type=search",
                id: "2165236624",
                title: "穿衣指南",
                width: 234,
                height: 469
        }*/

        List<Selectable> images = page.getJson().jsonPath("$.images[*]").nodes();
        for (Selectable s : images) {
            Json image = new Json(s.get());
            PictureMaterial pictureMaterial = new PictureMaterial();
            pictureMaterial.setTitle(image.jsonPath("$.author").get());
            pictureMaterial.setImgUriOrigin(image.jsonPath("$.src").get());
            pictureMaterial.setDescription(String.format("width:%s/height:%s"
                    , image.jsonPath("$.width").get()
                    , image.jsonPath("$.height").get()));
            pictureMaterialMapper.insertSelective(pictureMaterial);
        }
        pageIndex++;
        long start = limit * pageIndex;
        page.addTargetRequest(String.format("https://www.douban.com/j/search_photo?q=穿着&limit=%s&start=%s", limit, start));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new DouBanChuanZhuo())
                .addUrl("https://www.douban.com/j/search_photo?q=穿着&limit=20&start=1")
                .run();
    }
}
