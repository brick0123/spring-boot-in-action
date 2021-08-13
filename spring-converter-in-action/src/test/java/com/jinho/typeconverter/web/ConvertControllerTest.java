package com.jinho.typeconverter.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class ConvertControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void test() throws Exception {
        final ResultActions actions = mockMvc.perform(get("/convert/fruit")
            .param("color", "RED"));

        actions.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("\"APPLE\""));
    }

}
