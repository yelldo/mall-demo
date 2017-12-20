package com.mmall.service.tempjob;

import com.mmall.dao.PictureMaterialMapper;
import com.mmall.pojo.PictureMaterial;
import com.mmall.util.JestHelper;
import com.mmall.util.SystemUtil;
import io.searchbox.client.JestClient;
import io.searchbox.core.Bulk;
import io.searchbox.core.BulkResult;
import io.searchbox.core.Index;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 向elasticsearch批量插入数据
 * <p>
 * Created by luzy on 2017/12/6.
 */
@Service
public class ElasticsearchService {

    @Resource
    private PictureMaterialMapper pictureMaterialMapper;

    public static void main(String[] args) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.matchQuery("id", "2"));
        System.out.println("matchQuery : " + SystemUtil.newLine() + sourceBuilder.toString());
        matchQuery :
        //{
        //    "query" : {
        //    "match" : {
        //        "id" : {                                       -- id : _source中的field
        //            "query" : "2",                             -- query : 查询的内容
        //            "operator" : "OR",                         -- operator : or 或者 and 的条件语句连接（类似sql语法中的or，and)
        //            "prefix_length" : 0,                       -- prefix_length :
        //            "max_expansions" : 50,                     -- max_expansions :
        //            "fuzzy_transpositions" : true,             -- fuzzy_transpositions : ture or false(default false, result: (ab → ba))
        //            "lenient" : false,                         -- lenient : The lenient parameter can be set to true to i gnore exceptions caused by data-type mismatches, such as trying to query a numeric field with a text query string. Defaults to false.
        //            "zero_terms_query" : "NONE",               -- zero_terms_query : none or all (default none) ?
        //            "boost" : 1.0                              其他没有列出来，高级使用的参数：
        //        }                                              minimum_should_match ...
        //    }
        //}
        //}

        //sourceBuilder.query(QueryBuilders.matchAllQuery());
        //System.out.println("matchAllQuery : " + SystemUtil.newLine() + sourceBuilder.toString());

        sourceBuilder.query(QueryBuilders.commonTermsQuery("id","200"));
        System.out.println("commonTermsQuery : " + SystemUtil.newLine() + sourceBuilder.toString());
    }

    public void dumpDataJob() {
        System.out.println(Thread.currentThread().getName() + " dumpDataJob start! ...");
        // 任务异步化
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    // 线程池容量初始化
                    ExecutorService executorService = Executors.newCachedThreadPool();
                    executorService.submit(() -> {
                        System.out.println("dumpData() method submit ...");
                        dumpData();
                    });
                } catch (Exception e) {

                }
            }
        }, 3000);
    }

    public void dumpData() {
        System.out.println("dumpData() method start ...");
        List<PictureMaterial> originalData = null;
        try {
            //originalData = pictureMaterialMapper.selectAll();
            originalData = pictureMaterialMapper.selectByLimit(0L, 100);

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Index> sources = new ArrayList<>();
        int i = 0;
        for (PictureMaterial ori : originalData) {
            sources.add(new Index.Builder(ori).id(ori.getId().toString()).build());
            i++;
            if (i % 500 == 0) {
                System.out.println("deal num 【" + i + "】 ... ");
            }

        }
        JestClient client = JestHelper.get();

        Bulk bulk = new Bulk.Builder().defaultIndex("picture_material").defaultType("wearing").addAction(sources).build();

        BulkResult result = null;
        try {
            System.out.println("client execute now ...");
            result = client.execute(bulk);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("result: " + result.getJsonString());
    }

    /**
     * 批量繁体转中文
     *
     * @param start
     * @param limit
     */
    /*public void updateBatch2(long start, int limit) {
        System.out.println(Thread.currentThread().getName() + " updateBatch2 start ...");
        long st = System.currentTimeMillis();
        int total = 0;
        // 每个线程任务执行总10000条记录，每批次更新1000条记录
        for (long i = start; i < start + 10000; i += limit) {
            List<PictureMaterial> pm = null;
            pm = pictureMaterialMapper.selectByLimit(i, limit);
            System.out.println(Thread.currentThread().getName() + " select total : " + pm.size());
            for (PictureMaterial en : pm) {
                en.setTitle(HanLP.convertToSimplifiedChinese(en.getTitle()));
                en.setDescription(HanLP.convertToSimplifiedChinese(en.getDescription()));
            }
            // 独立的线程抛异常的话，不会在控制台打印，所以这里手动catch住
            try {
                if (pm.size() < 1) continue;
                total = total + pictureMaterialMapper.updateBatchCaseWhen(pm);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error i = " + i);
            }
        }

        long et = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " spend : " + (et - st) / 1000 + " total : " + total + "start = " + start);
        System.out.println(Thread.currentThread().getName() + " updateBatch2 end ...");
    }*/

    /*public void hanConvertToSimple2() {
        System.out.println(Thread.currentThread().getName() + " hanConvertToSimple2 start! ...");
        // 任务异步化
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    // 线程池容量初始化
                    ExecutorService executorService = Executors.newFixedThreadPool(10);
                    *//**
     * 这个地方参数写的太死，需要重构优化下
     * 说明：
     * 因为数据库中当时的数据是4W+，id已经自增到5W+快6W；
     * 从第一个数据开始查起（即数据库index从0开始）
     * 0    10000   20000   30000   40000   50000  总共有6批数据要处理
     *//*
                    for (int i = 0; i < 60000; i += 10000) {
                        final int j = i;
                        System.out.println(Thread.currentThread().getName() + " ExecutorService : j - " + j);
                        // 这里submit一次使用一个线程实例，所以开启了6个线程
                        executorService.submit(() -> {
                            updateBatch2(j, 1000);
                        });
                    }
                } catch (Exception e) {

                }
            }
        }, 3000);
    }*/
}
