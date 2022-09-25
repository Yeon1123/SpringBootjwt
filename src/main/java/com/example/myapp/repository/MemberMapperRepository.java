package com.example.myapp.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.myapp.dto.MemberMapperDTO;

//mybatis용 인터페이스 매퍼
@Mapper
public interface MemberMapperRepository {
    
    void createMember(MemberMapperDTO memberMapperDTO);
}
