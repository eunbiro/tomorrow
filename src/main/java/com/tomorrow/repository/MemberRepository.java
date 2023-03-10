package com.tomorrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tomorrow.dto.MemberFormDto;
import com.tomorrow.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByUserId(String userId);

	// 아이디 찾기
	@Query("select m from Member m where m.userNm=:userNm and m.pNum=:pNum")
	public MemberFormDto findId(@Param("userNm") String userNm, @Param("pNum") String pNum);

	// 비밀번호 찾기
	@Query("select m from Member m where m.userId=:userId and m.pNum=:pNum")
	public MemberFormDto findPassword(@Param("userId") String userId, @Param("pNum") String pNum);

	// 비밀번호 수정 전 힌트 맞추기
	@Query("select m from Member m where m.hintA=:hintA and m.hintQ=:hintQ and m.userId=:userId")
	public MemberFormDto findHint(@Param("hintA") String hintA, @Param("hintQ") String hintQ, @Param("userId") String userId);

	// userId로 memberId 찾기
	@Query(value = "select member_id from member where user_id = :userId", nativeQuery = true)
	public Member findMemberId(@Param("userId") String userId);

	// memberId로 Member 정보 찾기?
	@Query(value = "select * from member where memberId = :memberId", nativeQuery = true)
	public Member findByEmplMemberId(@Param("memberId") Long memberId);
	
}
