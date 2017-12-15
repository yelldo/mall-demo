package com.mmall.controller.common;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.RequestLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;

import java.io.IOException;

/**
 * Created by luzy on 2017/12/13.
 */
public class TestControllerBase extends ParentController{
    // response
    protected void printResponse(Response response) throws IOException {
        RequestLine requestLine = response.getRequestLine();
        HttpHost host = response.getHost();
        int statusCode = response.getStatusLine().getStatusCode();
        Header[] headers = response.getHeaders();
        String responseBody = EntityUtils.toString(response.getEntity());
        System.out.println("requestLine:" + requestLine);
        System.out.println("host:" + host);
        System.out.println("statusCode:" + statusCode);
        System.out.println("headers:" + headers);
        System.out.println("responseBody:" + responseBody);
    }
}
