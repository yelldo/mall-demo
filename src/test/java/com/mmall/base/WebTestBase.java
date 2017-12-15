package com.mmall.base;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




/**
 * MVC测试基类
 * Created by luzy on 2017/12/11.
 */
@WebAppConfiguration
@Transactional
@TransactionConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(name="parent", locations = {"classpath:applicationContext.xml"}),
        @ContextConfiguration(name="child", locations = {"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
})
public class WebTestBase {
    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    // 参考文章- http://zhaozhiming.github.io/blog/2014/06/16/spring-mvc-unit-test-part-1/
    /*@Mock
    private MailService mailService;

    @InjectMocks
    MailController mailController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mailController).build();
    }*/

    @Test
    public void test() throws Exception {
        ///mmall/tempjob/testInit.do

        //mockMvc.perform(get("/mmall/tempjob/testInit.do").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
        mockMvc.perform(MockMvcRequestBuilders.get("/tempjob/testInit.do")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                //.andExpect(status().isNotFound());
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                //.andExpect(jsonPath("$.status").value(0));
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
