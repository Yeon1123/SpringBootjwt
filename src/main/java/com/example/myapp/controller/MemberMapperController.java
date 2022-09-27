package com.example.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.dto.MemberDTO;
import com.example.myapp.service.MemberMapperServiece;


@RestController
@RequestMapping("/members")
public class MemberMapperController {

    @Autowired
    MemberMapperServiece memberMapperServiece;

    //requestbody 이용하여 객체를 전송 (json 형태를 통으로 자바 객체로 변환)
    @PostMapping
    public MemberDTO createMember(@RequestBody MemberDTO memberDTO){
        return memberMapperServiece.createMember(memberDTO);
    }

    @GetMapping
    public List<MemberDTO> findAllMember(){
        List<MemberDTO> memberList = memberMapperServiece.findAllMember();
        return memberList;
    }

    @GetMapping("/{email}")
    public MemberDTO findMemberByEmail(@PathVariable("email") String email){
        MemberDTO member = memberMapperServiece.findMemberByEmail(email);
        return member;
    }

    @PostMapping(value="update/{email}")
    public MemberDTO changeMemberInfoByEmail(@PathVariable("email") String email, @RequestBody MemberDTO changedMember) {
        return memberMapperServiece.modifyMember(email, changedMember);
    }

    @DeleteMapping(value="delete/{email}")
    public MemberDTO.MemberReturnDTO changeMemberInfoByEmail(@PathVariable("email") String email) {
        return memberMapperServiece.deleteMemberByEmail(email);
    }
}
