package com.example.myapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@SpringBootTest
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    //기존 RestDocumentation설정을 어노테이션으로 변경
    // "index" 라는 디렉토리에 snippet을 추가함.
    // output 디렉토리를 설정해주지 않았으므로 디폴트 target/generated-snippets 하위에 생성된다
    @Test
    @WithMockUser(roles = "USER")
    public void GetHelloWorld() throws Exception {
        String hello = "hello world!";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello))
                .andDo(document("helloAPI"));
                // target/generated-snippets/index아래에 생성하고, 응답 payload를 받기 위해 사용
    }
}
