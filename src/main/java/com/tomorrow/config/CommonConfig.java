package com.tomorrow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration	
public class CommonConfig {
	
	@Bean		// @Configuration 얘랑 주로 같이 사용하고 싱글톤이 보장된다.
	public PasswordEncoder passwordEncoder() { // 비밀번호 암호화를 위해서 사용하는 빈(Bean)
		return new BCryptPasswordEncoder();
	}
}
