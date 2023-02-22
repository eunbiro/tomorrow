package com.tomorrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tomorrow.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Member findByUserId(String userId);
	
//	//프로필 이미지 띄우기
//	@Query(value = "select m from member m where m.userId = :userId and m.")
//	Member findByUserProfileImg(@Param("userId") String userId);
	
}
