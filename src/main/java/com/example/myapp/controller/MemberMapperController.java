package com.example.myapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.dto.MemberMapperDTO;
import com.example.myapp.service.MemberMapperServiece;

@RestController
@RequestMapping("/member")
public class MemberMapperController {

    @Autowired
    MemberMapperServiece memberMapperServiece;

    //requestbody 이용하여 객체를 전송 (json 형태를 통으로 자바 객체로 변환)
    @PostMapping("/new")
    public void insertMember(@RequestBody MemberMapperDTO member){
        memberMapperServiece.createMember(member);
    }
}
