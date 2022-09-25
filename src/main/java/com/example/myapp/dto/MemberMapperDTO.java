package com.example.myapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberMapperDTO {

    @JsonProperty(value="member_id")
	private Long memberId;

	@JsonProperty(value="member_email")
	private String memberEmail;

	@JsonProperty(value="member_password")
	private String memberPassword;

	@JsonProperty(value="member_name")
	private String memberName;

	// 아래 생성자는 회원가입 때 필요한 정보만 모아놓은 생성자로 미리 만들어둡시다 :D
	public MemberMapperDTO (String memberEmail, String memberPassword, String memberName) {
	this.memberEmail = memberEmail;
	this.memberPassword = memberPassword;
	this.memberName = memberName;
	}
}
