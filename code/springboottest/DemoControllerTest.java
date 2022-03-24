package com.pangolin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.restlet.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.zen.pangolin.controller.rest.analysis.DemoController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        // 实例化方式一
        mockMvc = MockMvcBuilders.standaloneSetup(new DemoController()).build();
        // 实例化方式二
        // mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testHelloZuoYang() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println("--------testHello-----------");
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println("--------testHello-----------");
    }

    @Test
    public void testGetHello() throws Exception {
        MvcResult mvcResult =
            mockMvc.perform(MockMvcRequestBuilders.get("/hello/get/zhangsan").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println("--------testGetHello-----------");
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println("--------testGetHello-----------");
    }

    @Test
    public void testPostHello() throws Exception {
        MvcResult mvcResult = mockMvc
            .perform(MockMvcRequestBuilders.post("/hello/post").param("name", "POST_lisi")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            // .andExpect(MockMvcResultMatchers.content().string("Hello Tom!"))
            .andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println("--------testPostHello-----------");
        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println("--------testPostHello-----------");
    }

}
