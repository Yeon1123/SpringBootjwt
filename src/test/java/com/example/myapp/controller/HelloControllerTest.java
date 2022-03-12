package com.example.myapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;

@WebMvcTest
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    // restDocumentation을 사용하기 위한 설정
    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    // "index" 라는 디렉토리에 snippet을 추가함.
    // output 디렉토리를 설정해주지 않았으므로 디폴트 target/generated-snippets 하위에 생성된다
    @Test
    public void returnHelloWorld() throws Exception {
        String hello = "hello world!";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
                // target/generated-snippets/index아래에 생성하고, 응답 payload를 받기 위해 사용
    }
}
