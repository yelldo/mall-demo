package com.mmall.util;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jest is a Java HTTP Rest client for ElasticSearch.
 * <p>
 * 使用的单例，因为官方是这么说明的：
 * JestClient is designed to be singleton, don't construct it for each request!
 * <p>
 * Created by luzy on 2017/12/11.
 */
public class JestHelper {
    private final Logger log = LoggerFactory.getLogger(JestHelper.class);

    private static JestClient jestClient = null;

    /*private JestHelper() {
        // Construct a new Jest client according to configuration via factory
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig
                .Builder("http://localhost:9200")
                .multiThreaded(true)
                //Per default this implementation will create no more than 2 concurrent connections per given route
                //.defaultMaxTotalConnectionPerRoute(<YOUR_DESIRED_LEVEL_OF_CONCURRENCY_PER_ROUTE>)
                // and no more 20 connections in total
                //.maxTotalConnection(<YOUR_DESIRED_LEVEL_OF_CONCURRENCY_TOTAL>)
                .build());
        //JestClient client = factory.getObject();
    }*/

    public static JestClient get() {
        if (jestClient == null) {
            // Construct a new Jest client according to configuration via factory
            JestClientFactory factory = new JestClientFactory();
            factory.setHttpClientConfig(new HttpClientConfig.Builder("http://localhost:9200").multiThreaded(true)
                    //Per default this implementation will create no more than 2 concurrent connections per given route
                    //.defaultMaxTotalConnectionPerRoute(<YOUR_DESIRED_LEVEL_OF_CONCURRENCY_PER_ROUTE>)
                    // and no more 20 connections in total
                    //.maxTotalConnection(<YOUR_DESIRED_LEVEL_OF_CONCURRENCY_TOTAL>)
                    .build());
            jestClient = factory.getObject();
        }
        return jestClient;
    }

}
