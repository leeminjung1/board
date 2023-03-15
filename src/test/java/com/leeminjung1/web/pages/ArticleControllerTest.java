package com.leeminjung1.web.pages;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ArticleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void 모든게시글조회_성공() throws Exception {
        // given
        mvc.perform(get("/articles"))
                .andExpect(status().isOk());
        // when


        // then

    }

}