package com.mmall.test;

import com.mmall.base.TestBase;

/**
 * Created by luzy on 2017/12/15.
 */
public class TestJestClient2 extends TestBase{
        /*@Autowired
        private KlarticleDao klarticleDao;
        //得到JestClient实例
        public JestClient getClient()throws Exception{
            JestClientFactory factory = new JestClientFactory();
            factory.setHttpClientConfig(new HttpClientConfig
                    .Builder("http://127.0.0.1:9200")
                    .multiThreaded(true)
                    .build());

            return  factory.getObject();
        }
        *//**
         * 导入数据库数据到es
         * @throws Exception
         *//*
        @Test
        public void contextLoads() throws  Exception{
            JestClient client=getClient();
            Listlists=klarticleDao.findAll();
            for(Klarticle k:lists){
                Index index = new Index.Builder(k).index("indexdata").type("fulltext").id(k.getArcid()+"").build();
                System.out.println("添加索引----》"+k.getTitle());
                client.execute(index);
            }
            //批量新增的方式,效率更高
            Bulk.Builder bulkBuilder = new Bulk.Builder();
            for(Klarticle k:lists){
                Index index = new Index.Builder(k).index("indexdata").type("fulltext").id(k.getArcid()+"").build();
                bulkBuilder.addAction(index);
            }
            client.execute(bulkBuilder.build());
            client.shutdownClient();
        }
        //搜索测试
        @Test
        public void JestSearchTest()throws Exception{
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("content", "搜索"));
            Search search = new Search.Builder(searchSourceBuilder.toString())
                    // multiple index or types can be added.
                    .addIndex("indexdata")
                    .build();
            JestClient client =getClient();
            SearchResult result=  client.execute(search);
//          List> hits = result.getHits(Klarticle.class);
            Listarticles = result.getSourceAsObjectList(Klarticle.class);
            for(Klarticle k:articles){
                System.out.println("------->："+k.getTitle());
            }
        }
    }*/
}
