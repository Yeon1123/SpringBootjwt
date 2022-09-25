package com.example.myapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static com.example.myapp.config.ApiDocumentUtils.getDocumentRequest;


@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/generated-snippets", uriScheme = "http", uriHost = "docs.api.com")
@SpringBootTest
public class MemberMapperControllerTest {

    
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    public void GetHelloWorld() throws Exception {
        String content = "{\"member_email\": \"test@naver.com\", \"member_password\" : \"test\",\"member_name\": \"name\"}";

        ResultActions result = this.mvc.perform(
                post("/member/new", 1L)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        System.out.println(result);

        result.andExpect(status().isOk())
        .andDo(document("member-create",
                getDocumentRequest()
        ));
    }
}
