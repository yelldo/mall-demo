package com.mmall.controller.temp;

import com.hankcs.hanlp.HanLP;
import com.mmall.common.ServerResponse;
import com.mmall.controller.common.ParentController;
import com.mmall.service.tempjob.ElasticsearchService;
import com.mmall.service.tempjob.HanConvertService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by luzy on 2017/11/20.
 */
@Controller
@RequestMapping("/tempjob/")
public class TempJobController extends ParentController {

    @Resource
    private HanConvertService hanConvertService;
    @Resource
    private ElasticsearchService elasticsearchService;

    @RequestMapping(value = "testInit.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testInit(){
        return ServerResponse.createBySuccessMessage("init success! ...");
    }

    @RequestMapping(value = "hanConvertToSimple.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> hanConvertToSimple(){
        hanConvertService.hanConvertToSimple2();
        return ServerResponse.createBySuccessMessage("hanConvertToSimple ! ...");
    }

    @RequestMapping(value = "hanConvertToSimple0.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> hanConvertToSimple0(){
        hanConvertService.updateBathc0(25000, 100);
        return ServerResponse.createBySuccessMessage("hanConvertToSimple ! ...");
    }

    @RequestMapping(value = "dumpDataToElasticsearch.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> dumpDataToElasticsearch(){
        elasticsearchService.dumpDataJob();
        return ServerResponse.createBySuccessMessage("dumpDataToElasticsearch ! ...");
    }


    public static void main(String[] args) {
        //System.out.println(HanLP.convertToTraditionalChinese("用笔记本电脑写程序"));
        //System.out.println(HanLP.convertToSimplifiedChinese("「以後等妳當上皇后，就能買士多啤梨慶祝了」"));
        //System.out.println(HanLP.convertToSimplifiedChinese("用笔记本电脑写程序"));
        System.out.println(HanLP.convertToSimplifiedChinese("穿搭主題：我知道為什麼這一年我颷了5公斤...."));
    }
}
