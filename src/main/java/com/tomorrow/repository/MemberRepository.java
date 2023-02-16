package com.tomorrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	Member findByUserId(String userId);

}
