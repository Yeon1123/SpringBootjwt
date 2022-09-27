package com.example.myapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.myapp.dto.MemberDTO;

//mybatis용 인터페이스 매퍼
@Mapper
public interface MemberMapperRepository {
    
    public void createMember(MemberDTO memberDTO);

    public List<MemberDTO> findAllMember();

    public MemberDTO findMemberByEmail(String email);

    public void modifyMember(MemberDTO memberDTO);

    public void deleteMemberByEmail(String email);

}