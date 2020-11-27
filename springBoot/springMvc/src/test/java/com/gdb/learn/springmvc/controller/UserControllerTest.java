package com.gdb.learn.springmvc.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Controller 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;
    /**
     * 创建MockMvc用于模拟发送http请求
     */
    private MockMvc mvc;

    /**
     * MockMvc初始化
     */
    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
    }

    /**
     * 测试get请求
     */
    @Test
    public void userGetTest() {
        try {
            mvc.perform(MockMvcRequestBuilders.get("/user"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
                    //.andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
