package com.tomorrow.service;

import java.security.Principal;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.dto.PasswordDto;
import com.tomorrow.entity.Member;
import com.tomorrow.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageService {
	
	private final MemberRepository memberRepository;
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	
	public Model getSideImg(Model model, Principal principal) {   
	     MemberFormDto memberFormDto = memberService.getIdImgUrl(principal.getName());
	     return model.addAttribute("member", memberFormDto);
	}
	
	public void newPassword(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder, Principal principal, Model model) {
		
		//현재 dto에는 로그인 아이디 데이터가 흐르기 떄문에 findByUserId를 쓴다
		Member member = memberRepository.findByUserId(memberFormDto.getUserId());
		
		//dto에 적용시킬 건 password 뿐이기 때문에 memberFormDto.getPassword()로 쓴다
		member.updatePassword(memberFormDto.getPassword(), passwordEncoder);
		getSideImg(model, principal);
	}
	public  int comparePassword(PasswordDto passwordDto, String userId) {
		//가져온 유저 아이디로 멤버를 찾습니다.
		Member member = findMember(userId);
		
		//암호화된 비밀번호와 내가 입력한 비밀번호가 잘 맞는 지 확인 
		if(passwordEncoder.matches(passwordDto.getCheckPassword(), member.getPassword())) {
			//true 일 시 1을 반환
			return 1;
		}else {
			return 0;
		}
	}
	
	@Transactional(readOnly = true)
	public Member findMember(String userId) {

		return memberRepository.findByUserId(userId);
	}
	
}
