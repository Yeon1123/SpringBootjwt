package com.example.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.dto.MemberDTO;
import com.example.myapp.dto.MemberDTO.MemberReturnDTO;
import com.example.myapp.repository.MemberMapperRepository;

@Service
public class MemberMapperServiece {

    @Autowired
    MemberMapperRepository memberMapperRepository;

    public MemberDTO createMember(MemberDTO memberDTO)
    {
        memberMapperRepository.createMember(memberDTO);
        return findMemberByEmail(memberDTO.getMemberEmail());
    }

    public List<MemberDTO> findAllMember()
    {
        return memberMapperRepository.findAllMember();
    }

    public MemberDTO findMemberByEmail(String email)
    {
        return memberMapperRepository.findMemberByEmail(email);
    }

    public MemberDTO modifyMember(String email, MemberDTO memberDTO)
    {
        MemberDTO test = findMemberByEmail(email);
        if(test != null) {
            memberDTO.setMemberId(findMemberByEmail(email).getMemberId());
            memberMapperRepository.modifyMember(memberDTO);
            return memberDTO;
        }
        return null;
    }

    public MemberDTO.MemberReturnDTO deleteMemberByEmail(String email)
    {
        MemberDTO.MemberReturnDTO responseDto = new MemberReturnDTO(false);
        if(findMemberByEmail(email) != null) {
            memberMapperRepository.deleteMemberByEmail(email);
            responseDto.setPassFail(true);
        }
        return responseDto;
    }
}
