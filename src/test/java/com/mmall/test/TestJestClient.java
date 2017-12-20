package com.mmall.test;

import com.alibaba.fastjson.JSONObject;
import com.mmall.base.WebTestBase;
import com.mmall.pojo.elasticsearch.ESPictureMaterial;
import com.mmall.util.JestHelper;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        String script = "{\n" +
                "    \"script\" : \"ctx._source.tags+=tag1\",\n" +
                "    \"params\" : {\n" +
                "        \"tag1\" : \"bl    ue\"\n" +
                "    }\n" +
                "}";

        // 这个用法不行
        /*XContentBuilder script2 = XContentFactory.jsonBuilder()
                .startObject()
                //.field("doc", new JSONObject().put("views2",0))
                .prettyPrint()
                .endObject();*/

        JestResult result = client.execute(new Update.Builder(script).index("twitter").type("tweet").id("1").build());
        JSONObject extraResult = result.getSourceAsObject(JSONObject.class);
        System.out.println("result: " + result.getJsonString()+ ", extraResult: " + (extraResult == null ? "" : extraResult.toJSONString()));
    }

    @Test
    public void testPutOneDocument() throws Exception {
        // put 相当于覆盖了原来的文档 （也即是重新Index了一个新的文档，旧的文档被标记为 delete，
        // 虽然不会立即删除，但是会在某一时刻被elasticsearch删除）
    }

    @Test
    public void testDeleteOneDocument() throws Exception {
        JestResult result = client.execute(new Delete.Builder("1").index("twitter").type("tweet").build());
        System.out.println("result: " + result.getJsonString());
    }

    @Test
    public void testBulkOperations() throws Exception {
        /*Map<String,Object> source = new HashMap<>();
        source.put("sex", "male");
        Map<String, String> subSource = new HashMap<>();
        subSource.put("test1", "test1");
        subSource.put("test2", "test2");
        source.put("friends", subSource);*/

        ESPictureMaterial source1 = new ESPictureMaterial();
        source1.setId(31L);
        source1.setCreateTime(new Date());
        source1.setUpdateTime(new Date());
        source1.setTitle("i am a demo title31");
        source1.setImgUri("www.baidu.com31");
        source1.setImgUriOrigin("www.baidu.com31");
        ESPictureMaterial source2 = new ESPictureMaterial();
        source2.setId(32L);
        source2.setCreateTime(new Date());
        source2.setUpdateTime(new Date());
        source2.setTitle("i am a demo title32");
        source2.setImgUri("www.baidu.com32");
        source2.setImgUriOrigin("www.baidu.com32");

        /*List<Index> source0 = Arrays.asList(
                new Index.Builder(source1).id(source1.getId().toString()).build()
                ,new Index.Builder(source2).id(source2.getId().toString()).build()
        );*/
        List<Index> source0 = new ArrayList<>();
        source0.add(new Index.Builder(source1).id(source1.getId().toString()).build());
        source0.add(new Index.Builder(source2).id(source2.getId().toString()).build());


        Bulk bulk = new Bulk.Builder()
                .defaultIndex("twitter")
                .defaultType("tweet")
                .addAction(source0)
                //.addAction(new Index.Builder(source1).build())
                //.addAction(new Index.Builder(source2).build())
                //.addAction(new Delete.Builder("1").index("twitter").type("tweet").build())
                .build();

        BulkResult result = client.execute(bulk);
        System.out.println("result: " + result.getJsonString());
    }

    // 搜索数据
    @Test
    public void search() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));

        Search search = new Search.Builder(searchSourceBuilder.toString())
                // multiple index or types can be added.
                .addIndex("twitter")
                .addType("tweet")
                .build();
        SearchResult result = client.execute(search);
        //List<SearchResult.Hit<Article, Void>> hits = result.getHits(Article.class);
        List<JSONObject> extraResult = result.getSourceAsObjectList(JSONObject.class);
        /*StringBuffer printBuffer = new StringBuffer();
        for (JSONObject json : extraResult) {
            printBuffer.append(json.toJSONString());
        }*/
    }

    // 批量删除
    @Test
    public void deleteBatch() throws Exception {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("id", "kimchy"));

        Search search = new Search.Builder(sourceBuilder.toString())
                // multiple index or types can be added.
                .addIndex("picture_material")
                .addType("wearing")
                .build();
        SearchResult result = client.execute(search);
        //List<SearchResult.Hit<Article, Void>> hits = result.getHits(Article.class);
        List<JSONObject> extraResult = result.getSourceAsObjectList(JSONObject.class);
    }

}
