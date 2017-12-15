package com.mmall.service.tempjob;

import com.hankcs.hanlp.HanLP;
import com.mmall.dao.PictureMaterialMapper;
import com.mmall.pojo.PictureMaterial;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by luzy on 2017/12/6.
 */
@Service
public class HanConvertService {

    @Resource
    private PictureMaterialMapper pictureMaterialMapper;

    /**
     * 小数据量或单条更新demo
     */
    public void updateBatch(long start) {
        long st = System.currentTimeMillis();
        List<PictureMaterial> pm = pictureMaterialMapper.selectByLimit(start + 1, 1000);
        for (PictureMaterial en : pm) {
            en.setTitle(HanLP.convertToSimplifiedChinese(en.getTitle()));
            en.setDescription(HanLP.convertToSimplifiedChinese(en.getDescription()));
        }
        int total = pictureMaterialMapper.updateBatchCaseWhen(pm);
        long et = System.currentTimeMillis();
        System.out.println("currentTread : " + Thread.currentThread().getName() + " spend : " + (et - st) / 1000 + "total : " + total);
    }

    /**
     * 批量繁体转中文
     *
     * @param start
     * @param limit
     */
    public void updateBatch2(long start, int limit) {
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
    }

    /**
     * 批量更新如果出错，可以用这个方法来找出具体是哪个数据更新有问题，修改即可继续
     *
     * @param start
     * @param limit
     */
    public void updateBathc0(long start, int limit) {
        System.out.println(Thread.currentThread().getName() + " updateBatch2 start ...");
        long st = System.currentTimeMillis();
        int total = 0;
        // 每个线程任务执行总10000条记录，每批次更新1000条记录
        for (long i = start; i < start + 110; i += limit) {
            List<PictureMaterial> pm = pictureMaterialMapper.selectByLimit(i, limit);
            System.out.println(Thread.currentThread().getName() + " select total : " + pm.size());
            for (PictureMaterial en : pm) {
                en.setTitle(HanLP.convertToSimplifiedChinese(en.getTitle()));
                en.setDescription(HanLP.convertToSimplifiedChinese(en.getDescription()));
            }
            try {
                // 如果查询到的数据集为空的话，不允许执行批量更新的方法，否则mybatis拼接sql语句的时候会出错
                if (pm.size() < 1) continue;
                total = total + pictureMaterialMapper.updateBatchCaseWhen(pm);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("更新出错，开始处 - error i = " + i);
            }
        }

        long et = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " spend : " + (et - st) / 1000 + " total : " + total + "start = " + start);
        System.out.println(Thread.currentThread().getName() + " updateBatch2 end ...");
    }

    public void hanConvertToSimple2() {
        System.out.println(Thread.currentThread().getName() + " hanConvertToSimple2 start! ...");
        // 任务异步化
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    // 线程池容量初始化
                    ExecutorService executorService = Executors.newFixedThreadPool(10);
                    /**
                     * 这个地方参数写的太死，需要重构优化下
                     * 说明：
                     * 因为数据库中当时的数据是4W+，id已经自增到5W+快6W；
                     * 从第一个数据开始查起（即数据库index从0开始）
                     * 0    10000   20000   30000   40000   50000  总共有6批数据要处理
                     */
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
    }
}
