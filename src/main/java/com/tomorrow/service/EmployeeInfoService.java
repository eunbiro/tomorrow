package com.tomorrow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeInfoService {
	/* TODO
	 * 
	 * 1. 매장선택 후 알바생 정보 불러오기
	 * (알바생 기본 정보: 이름, 전화번호)
	 * (디폴트 값으로 줄 것 - 근무상태: 승인대기) 
	 * 
	 * 2. 승인 누르면 근무상태 바뀌게 하기... 
	 * 승인대기 -> 승인완료 
	 * 권한등급 유저 -> 알바 
	 * 
	 * 3. 
	 * */
}
