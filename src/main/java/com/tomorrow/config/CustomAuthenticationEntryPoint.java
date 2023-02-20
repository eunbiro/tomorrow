package com.tomorrow.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

// 인증되지 않은 사용자가 리소스를 요청 할 경우 어떻게 처리할지에 관한 클래스
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");	// (401 에러, "에러메세지")
		response.sendRedirect("/intro");
	}

	
}
