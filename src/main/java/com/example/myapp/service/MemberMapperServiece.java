package com.example.myapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.dto.MemberMapperDTO;
import com.example.myapp.repository.MemberMapperRepository;

@Service
public class MemberMapperServiece {

    @Autowired
    MemberMapperRepository memberMapperRepository;

    public void createMember(MemberMapperDTO memberMapperDTO)
    {
        memberMapperRepository.createMember(memberMapperDTO);
    }
    
}
