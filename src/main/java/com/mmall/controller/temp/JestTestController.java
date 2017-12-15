package com.mmall.controller.temp;

import com.alibaba.fastjson.JSONObject;
import com.mmall.common.ServerResponse;
import com.mmall.controller.common.TestControllerBase;
import com.mmall.util.JestHelper;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.*;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.mapping.PutMapping;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luzy on 2017/11/20.
 */
@Controller
@RequestMapping("/jest/")
public class JestTestController extends TestControllerBase {

    // check JestClient
    @RequestMapping(value = "test.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> test() throws IOException {
        JestClient jestClient = JestHelper.get();
        System.out.println(jestClient);
        return ServerResponse.createBySuccessMessage("putOne started ...");
    }

    @RequestMapping(value = "testCreateIndex.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testCreateIndex() throws IOException {
        JestClient jestClient = JestHelper.get();
        JestResult result = jestClient.execute(new CreateIndex.Builder("demom").build());

        return ServerResponse.createBySuccessMessage(result.getJsonString());
    }

    @RequestMapping(value = "testCreateIndexBySettings.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testCreateIndexBySettings() throws IOException {
        JestClient jestClient = JestHelper.get();
        // using a JSON formatted string
        String settings = "\"settings\" : {\n" + "        \"number_of_shards\" : 5,\n" + "        \"number_of_replicas\" : 1\n" + "    }\n";
        JestResult result = jestClient.execute(new CreateIndex.Builder("test1214").settings(Settings.builder().loadFromSource(settings).build().getAsMap()).build());

        //using the SettingsBuilder helper class from Elasticsearch
        Settings.Builder settingsBuilder = Settings.builder();
        settingsBuilder.put("number_of_shards", 5);
        settingsBuilder.put("number_of_replicas", 1);

        JestResult result2 = jestClient.execute(new CreateIndex.Builder("test1214").settings(settingsBuilder.build().getAsMap()).build());

        return ServerResponse.createBySuccessMessage(result.getJsonString());
    }

    // Creating an Index Mapping
    @RequestMapping(value = "testCreateIndex2.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testCreateIndex2() throws IOException {
        JestClient jestClient = JestHelper.get();

        // pass the mapping source as a JSON formatted string
        PutMapping putMapping = new PutMapping.Builder("my_index", "my_type", "{ \"my_type\" : { \"properties\" : { \"message\" : {\"type\" : \"string\", \"store\" : \"yes\"} } } }").build();
        JestResult result = jestClient.execute(putMapping);

        // The helper class DocumentMapper.Builder from Elasticsearch can also be used to create the mapping source.
        /*import org.elasticsearch.index.mapper.DocumentMapper;
        import org.elasticsearch.index.mapper.core.StringFieldMapper;
        import org.elasticsearch.index.mapper.object.RootObjectMapper;

        RootObjectMapper.Builder rootObjectMapperBuilder = new RootObjectMapper.Builder("my_mapping_name").add(new StringFieldMapper.Builder("message").store(true));
        DocumentMapper documentMapper = new DocumentMapper.Builder("my_index", null, rootObjectMapperBuilder).build(null);
        String expectedMappingSource = documentMapper.mappingSource().toString();
        PutMapping putMapping = new PutMapping.Builder("my_index", "my_type", expectedMappingSource).build();
        jestClient.execute(putMapping);*/

        return ServerResponse.createBySuccessMessage(result.getJsonString());
    }

    // Indexing Documents
    @RequestMapping(value = "testIndexOneDocument.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testIndexOneDocument() throws IOException {
        JestClient jestClient = JestHelper.get();

        //1. via JSON String
        //String source = "{\"user\":\"kimchy\"}";

        //2. creating JSON via ElasticSearch JSONBuilder
        /*String source2 = jsonBuilder()
                .startObject()
                .field("user", "kimchy")
                .field("postDate", "date")
                .field("message", "trying out Elastic Search")
                .endObject().string();*/

        //3. via Map
        Map<String, String> source = new LinkedHashMap<String,String>();
        source.put("user", "kimchy");

        //4. via POJO
        /*Article source = new Article();
        source.setAuthor("John Ronald Reuel Tolkien");
        source.setContent("The Lord of the Rings is an epic high fantasy novel");*/

        // 如果不指定id的话，elasticsearch会自动生成id（generate_id 不是单纯的数字）
        Index index = new Index.Builder(source).index("twitter").type("tweet")/*.id("1")*/.build();
        JestResult result = jestClient.execute(index);
        return ServerResponse.createBySuccessMessage(result.getJsonString());
    }

    // Searching Documents
    @RequestMapping(value = "testSearchDocuments.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testSearchDocuments() throws IOException {
        JestClient jestClient = JestHelper.get();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));

        Search search = new Search.Builder(searchSourceBuilder.toString())
                // multiple index or types can be added.
                .addIndex("twitter")
                .addType("tweet")
                .build();
        SearchResult result = jestClient.execute(search);
        //List<SearchResult.Hit<Article, Void>> hits = result.getHits(Article.class);
        List<JSONObject> extraResult = result.getSourceAsObjectList(JSONObject.class);
        StringBuffer printBuffer = new StringBuffer();
        for (JSONObject json : extraResult) {
            printBuffer.append(json.toJSONString());
        }
        return ServerResponse.createBySuccessMessage(printBuffer.toString());
    }

    // Getting Documents
    @RequestMapping(value = "testGetDocuments.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testGetDocuments() throws IOException {
        JestClient jestClient = JestHelper.get();

        //Get get = new Get.Builder("twitter", "1").type("tweet").build();
        //JestResult result = client.execute(get);
        //Article article = result.getSourceAsObject(Article.class);

        Get get = new Get.Builder("twitter", "1").type("tweet").build();
        JestResult result = jestClient.execute(get);
        JSONObject extraResult = result.getSourceAsObject(JSONObject.class);

        return ServerResponse.createBySuccessMessage("result: " + result.getJsonString()+ ", extraResult: " + (extraResult == null ? "" : extraResult.toJSONString()));
    }


    // Updating Documents
    @RequestMapping(value = "testUpdateDocuments.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> testUpdateDocuments() throws IOException {
        // 注意事项： update field的时候，前提是要存在这个field
        JestClient jestClient = JestHelper.get();
        String script = "{\n" +
                "    \"script\" : \"ctx._source.tags += tag\",\n" +
                "    \"params\" : {\n" +
                "        \"tag\" : \"blue\"\n" +
                "    }\n" +
                "}";

        JestResult result = jestClient.execute(new Update.Builder(script).index("twitter").type("tweet").id("1").build());
        JSONObject extraResult = result.getSourceAsObject(JSONObject.class);
        return ServerResponse.createBySuccessMessage("result: " + result.getJsonString()+ ", extraResult: " + (extraResult == null ? "" : extraResult.toJSONString()));
    }

    public static void main(String[] args) throws IOException {
        String script = XContentFactory.jsonBuilder()
                .startObject()
                .field("doc", new JSONObject().put("views",0))
                .endObject().toString();
        System.out.println(script);
    }

}
