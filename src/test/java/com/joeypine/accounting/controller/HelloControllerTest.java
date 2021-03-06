package com.joeypine.accounting.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HelloControllerTest {

    private MockMvc mockMvc;


    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    public void testSayHello() throws Exception {
        //Arrange & Act & Assert
        mockMvc.perform(get("/v1.0/greeting").contentType("application/json")
                .param("name", "World"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"id\":1,\"name\":\"Hello,World\"}"));
    }
}
