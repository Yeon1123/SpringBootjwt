package com.example.myapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
	
    @JsonProperty(value="member_id")
	private int memberId;

	@JsonProperty(value="member_email")
	private String memberEmail;

	@JsonProperty(value="member_password")
	private String memberPassword;

	@JsonProperty(value="member_name")
	private String memberName;

	public MemberDTO (String memberEmail, String memberPassword, String memberName) {
		this.memberEmail = memberEmail;
		this.memberPassword = memberPassword;
		this.memberName = memberName;
	}

	@Data
	@AllArgsConstructor
    public static class MemberReturnDTO {

		private boolean passFail;
		
	}
}