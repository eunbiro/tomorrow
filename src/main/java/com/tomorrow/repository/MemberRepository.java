package com.tomorrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tomorrow.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Member findByUserId(String userId);
	
	//아이디 찾기
	@Query("select m.userId from Member m where m.userNm = :name and m.pNum = :pNum")
	public Member findByMemberId(@Param("name") String name, @Param("pNum") String pNum);
	
}
