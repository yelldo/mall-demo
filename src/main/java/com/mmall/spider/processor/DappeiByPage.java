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
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

import java.sql.Timestamp;
import java.util.List;

/**
 * url返回json的例子
 * <p>
 * 豆瓣 - search - 穿着
 * Created by luzy on 2017/11/20.
 */
public class DappeiByPage implements PageProcessor {
    /**
     * 0.针对 SocketTimeoutException : read timed out 这个问题的解决方案有如下：
     *   a.建议把线程和timeout设置的久一点，还有及时使用代理，随时切换 (github上webmagic的issue的回答)
     * 1.几时使用代理，防止ip被反爬 ，做法？
     * 2.selenium 模拟登陆，这个方案必须得安装有chrome浏览器客户端，我使用的是 chromeDriver.exe
     *   看来是只能在window操作系统上使用了
     * 3.考虑断点续爬的情况，目前应该是没有这个需要
     * 4.考虑 RedisScheduler 的使用
     * 5.待续 。。。
     */

    private PictureMaterialMapper pictureMaterialMapper = (PictureMaterialMapper) SpringContextUtil.getBean("pictureMaterialMapper");
    //private PictureMaterial2Mapper pictureMaterialMapper = (PictureMaterial2Mapper) SpringContextUtil.getBean("pictureMaterial2Mapper");

    private Site site = Site.me().setDomain("dappei.com")
            .setSleepTime(3000)
            .setTimeOut(15000) // 增加连接超时时间，降低网络不好的情况下对爬虫的影响
            .setRetryTimes(5) // 增加重试次数，可以保证抓的更全一些
            .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

    private final String PAGE_LIST = "https://dappei\\.com/photos\\?category=female&order=id&page=\\d+";
    private final String IMG_INFO = "https://dappei\\.com/photos/\\d+";
    private int pageIndex = 1;
    private int limit = 200;
    private int start = 1;

    public DappeiByPage(){}

    public DappeiByPage(int limit, int start) {
        this.limit = limit;
        this.start = start;
        this.pageIndex = start;
    }

    @Override
    public void process(Page page) {
        if ((pageIndex >= start + limit - 1)) {
            return;
        }
        // 列表页
        if (page.getUrl().regex(PAGE_LIST).match()) {
            List<String> links = page.getHtml().xpath("//div[@id='photos-list']").links().regex(IMG_INFO).all();
            page.addTargetRequests(links);
            if (CollectionUtils.isNotEmpty(links)) {
                pageIndex++;
                page.addTargetRequest("https://dappei.com/photos?category=female&order=id&page=" + pageIndex);
            }
        }
        // 图片详情页
        if (page.getUrl().regex(IMG_INFO).match()) {
            PictureMaterial pictureMaterial = new PictureMaterial();
            //PictureMaterial2 pictureMaterial = new PictureMaterial2();
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
        Spider.create(new DappeiByPage())
                .setScheduler(new FileCacheQueueScheduler("E:\\git_download\\Crawler_Projects"))
                .addUrl("https://dappei.com/photos?category=female&order=id&page=1")
                .run();
    }
}
