package com.example.sns.domain.member.dto;

import java.time.LocalDate;

public record MemberDto(Long id, String nickname, String email, LocalDate birthDay) {

}
