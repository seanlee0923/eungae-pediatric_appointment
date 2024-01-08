package com.playdata.eungae.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.playdata.eungae.member.dto.MemberUpdateRequestDto;
import com.playdata.eungae.member.dto.MemberUpdateResponseDto;
import com.playdata.eungae.member.dto.RequestFavoriesDto;
import com.playdata.eungae.member.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/my")
public class MemberApiController {

	private final MemberService memberService;

	@PatchMapping("/profile/form/{memberSeq}")
	public MemberUpdateResponseDto updateMemberInfo(@PathVariable Long memberSeq, 
							@RequestBody MemberUpdateRequestDto updateRequestDto) {
		return memberService.updateMemberInfo(memberSeq, updateRequestDto);
	}

	@PostMapping("/hospital")
	@ResponseStatus(HttpStatus.CREATED)
	public String appendFavorites(@RequestBody @Valid RequestFavoriesDto requestFavoriesDto) {
		memberService.appendFavorites(requestFavoriesDto);
		return "Favorites have been successfully appended";
	}

	@PatchMapping("/hospital")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String removeFavorites(@RequestBody @Valid RequestFavoriesDto requestFavoriesDto) {
		memberService.removeFavorites(requestFavoriesDto);
		return "Favorites have been successfully deleted";
	}
}