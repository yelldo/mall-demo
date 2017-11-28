package com.mmall.controller.spider;

import com.mmall.common.ServerResponse;
import com.mmall.controller.common.ParentController;
import com.mmall.spider.processor.Dappei;
import com.mmall.spider.processor.DouBanChuanZhuo;
import com.mmall.spider.processor.SinaBlog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

/**
 * Created by luzy on 2017/11/20.
 */
@Controller
@RequestMapping("/spider/")
public class SpiderController extends ParentController {

    @RequestMapping(value = "testInit.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testInit(){
        return ServerResponse.createBySuccessMessage("init success! ...");
    }

    @RequestMapping(value = "sinaBlogProcessor.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> sinaBlogProcessor(){
        Spider.create(new SinaBlog()).addUrl("http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html")
                //.addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
                //.addPipeline(new JsonFilePipeline("E:\\git_download\\Crawler_Projects"))
                //.addPipeline(new JsonFilePipeline(("/usr/local/file_store/sinablog")))
                .runAsync();
                //.run();
        return ServerResponse.createBySuccessMessage("sinaBlogProcessor started ...");
    }

    @RequestMapping(value = "douBanChuanZhuoProcessor.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> douBanChuanZhuoProcessor(){
        Spider.create(new DouBanChuanZhuo())
                .addUrl("https://www.douban.com/j/search_photo?q=穿着&limit=20&start=1")
                //.runAsync();
                .run();
        return ServerResponse.createBySuccessMessage("douBanChuanZhuoProcessor started ...");
    }

    @RequestMapping(value = "dappeiProcessor.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> dappeiProcessor(){
        Spider.create(new Dappei())
                .addUrl("https://dappei.com/photos?category=male&order=id&page=1")
                .thread(5)
                .runAsync();
        return ServerResponse.createBySuccessMessage("dappeiProcessor started ...");
    }

    @RequestMapping(value = "dappeiProcessor2.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> dappeiProcessor2(){
        Spider.create(new Dappei())
                .addUrl("https://dappei.com/photos?category=female&order=id&page=1")
                //.setScheduler(new FileCacheQueueScheduler("E:\\git_download\\Crawler_Projects"))
                .thread(10)
                //.run();
                .runAsync();
        return ServerResponse.createBySuccessMessage("dappeiProcessor2 started ...");
    }
}
