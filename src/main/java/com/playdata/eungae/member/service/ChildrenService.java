package com.playdata.eungae.member.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.playdata.eungae.file.ResultFileStore;
import com.playdata.eungae.member.domain.Children;
import com.playdata.eungae.member.domain.Member;
import com.playdata.eungae.member.dto.ChildrenDto;
import com.playdata.eungae.member.dto.ChildrenRequestDto;
import com.playdata.eungae.member.repository.ChildrenRepository;
import com.playdata.eungae.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChildrenService {

	private final ChildrenRepository childrenRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void createChildren(
		ChildrenRequestDto childrenRequestDto,
		ResultFileStore resultFileStore,
		String email
	) {

		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalStateException("해당 이메일의 사용자가 존재하지 않습니다: " + email));

		Children children = ChildrenRequestDto.toEntity(childrenRequestDto, resultFileStore.getStoreFileName());
		children.setMember(member);
		childrenRepository.save(children);
	}

	@Transactional
	public void deleteChild(Long childrenSeq) {
		Children children = childrenRepository.findBychildrenSeq(childrenSeq)
			.orElseThrow(
				() -> new NoSuchElementException("Child not found by childrenSeq {%s}".formatted(childrenSeq)));

		children.deleted();
	}

	@Transactional(readOnly = true)
	public List<ChildrenDto> getAllChildrenByMemberSeq(long memberSeq) {
		return childrenRepository.findAllByMemberMemberSeq(memberSeq)
			.stream()
			.map(ChildrenDto::toDto)
			.peek(dto -> {
				String formattedDate = formatDate(dto.getBirthDate());
				dto.setBirthDate(formattedDate);
			})
			.collect(Collectors.toList());
	}

	private String formatDate(String dateStr) {
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

		try {
			return outputFormat.format(inputFormat.parse(dateStr));
		} catch (ParseException e) {
			return dateStr;
		}
	}

}
