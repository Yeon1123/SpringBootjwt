package com.example.myapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.example.myapp.dto.MemberDTO;
import com.example.myapp.dto.MemberDTO.MemberReturnDTO;
import com.example.myapp.service.MemberMapperServiece;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static com.example.myapp.config.ApiDocumentUtils.getDocumentRequest;
import static com.example.myapp.config.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;

// @SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
@WebMvcTest(MemberMapperController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class MemberMapperControllerTest {
    //@Spring Boot Test와 @WebMvcTest의 차이 : 전체 테스트 / 컨트롤러단 레이어 테스트
    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private MemberMapperServiece memberMapperServiece;


    @BeforeEach()
    public void setup(RestDocumentationContextProvider doc)
    {
        //Init MockMvc Object and build
        //한글깨짐 방지
        this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(doc))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    private String testOrginEmail = "test@naver.com";

    MemberDTO firstMemberDTO = MemberDTO.builder()
        .memberEmail("test@naver.com")
        .memberPassword("test")
        .memberName("yujy")
        .build();

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("사용자_생성")
    public void A_test() throws Exception {

        MemberDTO returnMemberDTO = MemberDTO.builder()
            .memberId(1)
            .memberEmail("test@naver.com")
            .memberPassword("test")
            .memberName("yujy")
            .build();

        given(memberMapperServiece.createMember(firstMemberDTO)).willReturn(returnMemberDTO);
        
        String content = "{\"member_email\":\"" + firstMemberDTO.getMemberEmail() + "\", \"member_password\":\"" + firstMemberDTO.getMemberPassword() + "\", \"member_name\": \"" + firstMemberDTO.getMemberName() + "\"}";

        ResultActions result = this.mvc.perform(
            RestDocumentationRequestBuilders.post("/members", 1L)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        result
        .andExpect(status().isOk())
        .andDo(
            document("member-create",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields (
                fieldWithPath("member_email").description("사용자 이메일").attributes(key("type").value("String")),
                fieldWithPath("member_password").description("사용자 비밀번호").attributes(key("type").value("String")),
                fieldWithPath("member_name").description("사용자 이름").attributes(key("type").value("String"))
            ),
            responseFields(
                fieldWithPath("member_id").description("사용자 인덱스"),
                fieldWithPath("member_email").description("등록된 이메일"),
                fieldWithPath("member_password").description("등록된 사용자 비밀번호"),
                fieldWithPath("member_name").description("등록된 사용자명")
            )
        ));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("사용자_전체조회")
    public void B_test() throws Exception {
        MemberDTO fstMemberDTO = MemberDTO.builder()
            .memberId(1)
            .memberEmail("test1@naver.com")
            .memberPassword("test1")
            .memberName("mili")
            .build();

        MemberDTO secondMemberDTO = MemberDTO.builder()
            .memberId(2)
            .memberEmail("test2@naver.com")
            .memberPassword("test2")
            .memberName("james")
            .build();
        
        List<MemberDTO> memberList = new ArrayList<>();
        memberList.add(fstMemberDTO);
        memberList.add(secondMemberDTO);

        //given : 내가 이런 자료를 주면 (안 줄수도 있고..)  Will Return memberList. 이런 정답을 받아와야 한다!
        given(memberMapperServiece.findAllMember()).willReturn(memberList);

        //when : 테스트 케이스
        ResultActions result = this.mvc.perform(
            get("/members").accept(MediaType.APPLICATION_JSON)
        );    
 
        //then
        result
            .andExpect(status().isOk())
            .andDo(
                document("members-find",
                getDocumentRequest(),
                getDocumentResponse(),
                responseFields(
                    fieldWithPath("[].member_id").description("사용자 인덱스"),
                    fieldWithPath("[].member_email").description("사용자 이메일"),
                    fieldWithPath("[].member_password").description("사용자명"),
                    fieldWithPath("[].member_name").description("사용자 비밀번호")
                )
            ));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("사용자_단일조회")
    public void C_test() throws Exception {

        given(memberMapperServiece.findMemberByEmail(firstMemberDTO.getMemberEmail())).willReturn(firstMemberDTO);

        ResultActions result = this.mvc.perform(
            get("/members/{email}", testOrginEmail).accept(MediaType.APPLICATION_JSON)
        );    

        result.andExpect(status().isOk())
        .andDo(document("member-find",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                fieldWithPath("member_id").description("사용자 인덱스"),
                fieldWithPath("member_email").description("사용자 이메일"),
                fieldWithPath("member_password").description("사용자명"),
                fieldWithPath("member_name").description("사용자 비밀번호")
            )
        ));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("사용자_정보수정")
    public void D_test() throws Exception {

        MemberDTO changeMemberDTO = MemberDTO.builder()
        .memberEmail("changedYujy@naver.com")
        .memberPassword("changeTest")
        .memberName("changedYujy")
        .build();
        
        MemberDTO resultMemberDTO = MemberDTO.builder()
        .memberId(firstMemberDTO.getMemberId())
        .memberEmail("changedYujy@naver.com")
        .memberPassword("changeTest")
        .memberName("changedYujy")
        .build();
        
        given(memberMapperServiece.modifyMember(firstMemberDTO.getMemberEmail(), changeMemberDTO)).willReturn(resultMemberDTO);

        String content = "{\"member_email\":\"" + changeMemberDTO.getMemberEmail() + "\", \"member_password\":\"" + changeMemberDTO.getMemberPassword() + "\", \"member_name\": \"" + changeMemberDTO.getMemberName() + "\"}";

        ResultActions result = this.mvc.perform(
            RestDocumentationRequestBuilders.post("/members/update/{email}", firstMemberDTO.getMemberEmail())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
 
        result
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("member-update",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("email").description("수정 전 이메일")
            ),
            requestFields(
                fieldWithPath("member_email").description("수정 후 이메일").attributes(key("type").value("String")), 
                fieldWithPath("member_password").description("수정 후 사용자 비밀번호").attributes(key("type").value("String")), 
                fieldWithPath("member_name").description("수정 후 사용자명").attributes(key("type").value("String")) 
            ),
            responseFields(
                fieldWithPath("member_id").description("사용자 인덱스"),
                fieldWithPath("member_email").description("수정 후 이메일"),
                fieldWithPath("member_password").description("수정 후 사용자 비밀번호"),
                fieldWithPath("member_name").description("수정 후 사용자명")
            )
        ));
    }

    @Test
    @WithMockUser(roles = "USER")
    @DisplayName("사용자_삭제")
    public void E_test() throws Exception {

        MemberDTO.MemberReturnDTO responseDto = new MemberReturnDTO(true);
        given(memberMapperServiece.deleteMemberByEmail(firstMemberDTO.getMemberEmail())).willReturn(responseDto);

        ResultActions result = this.mvc.perform(
            RestDocumentationRequestBuilders.delete("/members/delete/{email}", firstMemberDTO.getMemberEmail())
        );    
 
        result.andExpect(status().isOk())
        .andDo(document("member-delete",
            getDocumentRequest(),
            getDocumentResponse(),
            pathParameters(
                parameterWithName("email").description("삭제 대상 이메일").attributes(key("tye").value("string"))
            ),
            responseFields(
                fieldWithPath("passFail").description("삭제 성공여부")
            )
        ));
    }
}
