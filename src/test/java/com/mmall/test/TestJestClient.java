package com.mmall.test;

import com.alibaba.fastjson.JSONObject;
import com.mmall.base.WebTestBase;
import com.mmall.util.JestHelper;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Update;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * JestClient test
 * Created by luzy on 2017/12/13.
 */
public class TestJestClient extends WebTestBase {

    private JestClient client;

    @Test
    public void testCreateIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jest/testCreateIndex.do")//
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))//
                .andExpect(status().isOk())//
                .andExpect(content().contentType("application/json;charset=UTF-8"))//
                .andDo(MockMvcResultHandlers.print())//
                .andReturn();
    }

    @Test
    public void testIndexOneDocument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jest/testIndexOneDocument.do")//
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))//
                .andExpect(status().isOk())//
                .andExpect(content().contentType("application/json;charset=UTF-8"))//
                .andDo(MockMvcResultHandlers.print())//
                .andReturn();
    }

    @Test
    public void testSearchDocuments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jest/testSearchDocuments.do")//
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))//
                .andExpect(status().isOk())//
                .andExpect(content().contentType("application/json;charset=UTF-8"))//
                .andDo(MockMvcResultHandlers.print())//
                .andReturn();
    }

    @Test
    public void testGetDocuments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jest/testGetDocuments.do")//
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))//
                .andExpect(status().isOk())//
                .andExpect(content().contentType("application/json;charset=UTF-8"))//
                .andDo(MockMvcResultHandlers.print())//
                .andReturn();
    }

    @Test
    public void testUpdateDocuments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jest/testUpdateDocuments.do")//
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))//
                .andExpect(status().isOk())//
                .andExpect(content().contentType("application/json;charset=UTF-8"))//
                .andDo(MockMvcResultHandlers.print())//
                .andReturn();
    }

    @Before
    public void initJestClient() {
        if (client == null) {
            client = JestHelper.get();
        }
    }

    @Test
    public void testUpdateDocuments2() throws Exception {
        /*String script = "{\n" +
                "    \"script\" : \"ctx._source.tags += tag\",\n" +
                "    \"params\" : {\n" +
                "        \"tag\" : \"blue\"\n" +
                "    }\n" +
                "}";*/

        //String script2 = ""
        XContentBuilder script = XContentFactory.jsonBuilder()
                .startObject()
                //.field("doc", new JSONObject().put("views2",0))
                .prettyPrint()
                .endObject();

        JestResult result = client.execute(new Update.Builder(script).index("twitter").type("tweet").id("1").build());
        JSONObject extraResult = result.getSourceAsObject(JSONObject.class);
        System.out.println("result: " + result.getJsonString()+ ", extraResult: " + (extraResult == null ? "" : extraResult.toJSONString()));
    }

}
