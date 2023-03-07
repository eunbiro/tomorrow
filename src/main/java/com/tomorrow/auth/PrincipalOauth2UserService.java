package com.tomorrow.auth;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.tomorrow.auth.userInfo.KakaoUserInfo;
import com.tomorrow.auth.userInfo.OAuth2UserInfo;
import com.tomorrow.constant.Role;
import com.tomorrow.entity.Member;
import com.tomorrow.repository.MemberRepository;


@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	@Autowired private MemberRepository memberRepository;
	@Autowired private PasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		OAuth2UserInfo oauth2UserInfo = null;
		String provider = userRequest.getClientRegistration().getRegistrationId();
		
		if (provider.equals("kakao")) {
			
			oauth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
		}
		
		String providerId = oauth2UserInfo.getProviderId();
		String username = provider + "_" + providerId;
		
		String uuid = UUID.randomUUID().toString().substring(0, 6);
		String password = bCryptPasswordEncoder.encode("패스워드" + uuid);
		String userNm = "카카오사용자" + uuid;
		String email = oauth2UserInfo.getEmail();
		Role role = Role.USER;
		
		Member byUserNm = memberRepository.findByUserId(username);
		
		if(byUserNm == null) {
			
			byUserNm = Member.OAuth2Register()
                    .username(username).password(password).name(userNm).role(role)
                    .provider(provider).providerId(providerId)
                    .build();
			memberRepository.save(byUserNm);
		}
		
		return new PrincipalDetails(byUserNm, oauth2UserInfo);
	}

}
