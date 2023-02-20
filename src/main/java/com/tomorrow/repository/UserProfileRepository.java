package com.tomorrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomorrow.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
	
//	UserProfile findByUserProfileNm(String profileName);
}
