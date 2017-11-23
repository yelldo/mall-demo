package com.mmall.spider.processor;

import com.alibaba.fastjson.JSONObject;
import com.mmall.dao.PictureMaterialMapper;
import com.mmall.pojo.PictureMaterial;
import com.mmall.util.SpringContextUtil;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.sql.Timestamp;
import java.util.List;

/**
 * url返回json的例子
 * <p>
 * 豆瓣 - search - 穿着
 * Created by luzy on 2017/11/20.
 */
public class Dappei implements PageProcessor {

    private PictureMaterialMapper pictureMaterialMapper = (PictureMaterialMapper) SpringContextUtil.getBean("pictureMaterialMapper");

    private Site site = Site.me().setDomain("dappei.com").setSleepTime(3000).setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private final String PAGE_LIST = "https://dappei\\.com/photos\\?category=female&order=id&page=\\d+";
    private final String IMG_INFO = "https://dappei\\.com/photos/\\d+";
    private int pageIndex = 1;

    @Override
    public void process(Page page) {
        // 列表页
        if (page.getUrl().regex(PAGE_LIST).match()) {
            List<String> links = page.getHtml().xpath("//div[@id='photos-list']").links().regex(IMG_INFO).all();
            page.addTargetRequests(links);
            if (CollectionUtils.isNotEmpty(links)) {
                pageIndex++;
                page.addTargetRequest("https://dappei.com/photos?category=female&order=id&page=" + pageIndex);
            }
            /*if (pageIndex == 1) {
                for (int i = 2; i <= 1003; i++) {
                    Request request = new Request();
                    page.addTargetRequest("https://dappei.com/photos?category=male&order=id&page=" + i);
                    pageIndex++;
                }
            }*/
        }
        // 图片详情页
        if (page.getUrl().regex(IMG_INFO).match()) {
            PictureMaterial pictureMaterial = new PictureMaterial();
            pictureMaterial.setTitle(page.getHtml().xpath("//div[@class='title']/h1/text()").get());
            JSONObject info = new JSONObject();
            // 标签
            List<String> lables = page.getHtml().xpath("//div[@class='related-tags-content']/a/text()").all();
            int i = 0;
            for (String l : lables) {
                info.put("attr" + i++, l);
            }
            // 图中商品
            List<String> products = page.getHtml().xpath("//div[@class='info-container']/p/text()").all();
            int j = 0;
            for (String p : products) {
                info.put("good" + j++, p);
            }
            // 发表时间
            info.put("publishTime", page.getHtml().xpath("//div[@class='photo-information']/div[@class='information']/span[@class='time']/span/text()"));
            info.put("descMore", page.getHtml().xpath("//div[@class='photo-information']/div[@class='description']//p/text()").all());
            // 简单描述
            info.put("desc", page.getHtml().xpath("//div[@class='photo-container']/div[@class='photo']/img/@alt"));
            // sex 男装 or 女装
            info.put("sex", "female");
            pictureMaterial.setDescription(info.toJSONString());
            pictureMaterial.setSourceUrl("https://dappei.com");
            pictureMaterial.setImgUriOrigin(page.getHtml().xpath("//div[@class='photo-container']/div[@class='photo']/img/@src").get());
            pictureMaterial.setCreateTime(new Timestamp(System.currentTimeMillis()));
            pictureMaterial.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            pictureMaterialMapper.insertSelective(pictureMaterial);
            //System.out.println(pictureMaterial);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new Dappei()).addUrl("https://dappei.com/photos?category=female&order=id&page=1").thread(5).run();
    }
}
