package com.mmall.controller.spider;

import com.mmall.common.ServerResponse;
import com.mmall.controller.common.ParentController;
import com.spider.demo.SinaBlogProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

/**
 * Created by tianhc on 2017/11/20.
 */
@Controller
@RequestMapping("/spider/")
public class SpiderController extends ParentController {

    @RequestMapping(value = "sinaBlogProcessor.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> sinaBlogProcessor(){
        Spider.create(new SinaBlogProcessor()).addUrl("http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html")
                //.addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
                //.addPipeline(new JsonFilePipeline("E:\\git_download\\Crawler_Projects"))
                //.addPipeline(new JsonFilePipeline(("/usr/local/file_store/sinablog")))
                .runAsync();
                //.run();
        return ServerResponse.createBySuccess();
    }
}
