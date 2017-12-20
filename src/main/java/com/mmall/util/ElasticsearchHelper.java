package com.mmall.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Elasticsearch Client :   Java High Level REST Client
 * 这个client用起来比较直观，易懂
 * <p>
 * Created by luzy on 2017/12/11.
 */
public class ElasticsearchHelper {
    private final Logger log = LoggerFactory.getLogger(ElasticsearchHelper.class);

    private static RestHighLevelClient client = null;

    public static RestHighLevelClient  get() {
        if (client == null) {
            client = new RestHighLevelClient(//
                    RestClient.builder(//
                            //new HttpHost("localhost", 9201, "http"),//
                            new HttpHost("localhost", 9200, "http")));
        }
        return client;
    }
}
