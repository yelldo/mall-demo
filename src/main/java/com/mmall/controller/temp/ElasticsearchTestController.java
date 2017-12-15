package com.mmall.controller.temp;

import com.mmall.common.ServerResponse;
import com.mmall.controller.common.TestControllerBase;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * Created by luzy on 2017/11/20.
 */
@Controller
@RequestMapping("/elastic/")
public class ElasticsearchTestController extends TestControllerBase {

    // elastisearch 接口测试
    @RequestMapping(value = "putOne.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> putOne() throws IOException {
        // initialization
        RestClient restClient = RestClient.builder(
                //new HttpHost("localhost", 9201, "http"),
                new HttpHost("localhost", 9200, "http")).build();
        Map<String, String> params = Collections.emptyMap();
        String jsonString = "{" + "\"user\":\"kimchy\"," + "\"postDate\":\"2013-01-30\"," + "\"message\":\"trying out Elasticsearch\"" + "}";
        // content-type
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        // request
        Response response = restClient.performRequest("PUT", "/posts/doc/1", params, entity);
        // response
        printResponse(response);


        return ServerResponse.createBySuccessMessage("putOne started ...");
    }

    @RequestMapping(value = "simpleGet.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> simpleGet() throws IOException {
        RestClient restClient = RestClient.builder(
                //new HttpHost("localhost", 9201, "http"),
                new HttpHost("localhost", 9200, "http")).build();

        //HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory consumerFactory =
        //        new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024);
        //restClient.performRequestAsync("GET", "/posts/_search", params, null, consumerFactory, responseListener);


        //Header[] headers = {new BasicHeader("header1", "value1"), new BasicHeader("header2", "value2")};
        //restClient.performRequestAsync("GET", "/", responseListener, headers);
        //Response response = restClient.performRequest("GET", "/", new BasicHeader("header", "value"));


        Response response = restClient.performRequest("GET", "/megacorp/employee/_search");
        printResponse(response);
        return ServerResponse.createBySuccessMessage("simpleGet started ...");
    }

    @RequestMapping(value = "simpleASyncRequest.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> simpleASyncRequest() throws IOException {
        RestClient restClient = RestClient.builder(
                //new HttpHost("localhost", 9201, "http"),
                new HttpHost("localhost", 9200, "http")).build();

        HttpEntity[] documents = new HttpEntity[3];
        final CountDownLatch latch = new CountDownLatch(documents.length);
        for (int i = 0; i < documents.length; i++) {
            restClient.performRequestAsync("PUT", "/posts/doc/" + i, Collections.<String, String>emptyMap(),
                    //let's assume that the documents are stored in an HttpEntity array
                    documents[i], new ResponseListener() {
                        @Override
                        public void onSuccess(Response response) {

                            latch.countDown();
                        }

                        @Override
                        public void onFailure(Exception exception) {

                            latch.countDown();
                        }
                    });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccessMessage("simpleASyncRequest started ...");
    }
}
