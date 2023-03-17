package com.konkuk.vocabulary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.konkuk.vocabulary.dto.MessageDTO;
import com.konkuk.vocabulary.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Controller~!~" , description = "Controller API Document")
public class SecurityController {
	
	@Autowired JwtService jwtService;
	
	/*
	 * Swagger의 자세한 사용법 : https://adjh54.tistory.com/72
	 * 접속 : http://localhost:8080/swagger-ui/index.html
	 */
	@Operation(summary = "컨트롤러" , description = "테스트용 API 입니다." , tags = {"views"})
	@RequestMapping(value = "/controller")
	public MessageDTO controller() {
		return MessageDTO.builder()
				.code(999999999)
				.message("message")
				.build();
	}
	
	@Operation(summary = "USER 사용자만 접근 가능" , description = "테스트용 API 입니다." , tags = {"views"})
	@RequestMapping(value = "/user" , method = RequestMethod.GET)
	public String userOnly() {
		return "userOnly";
	}
	
	@Operation(summary = "ADMIN 사용자만 접근 가능" , description = "테스트용 API 입니다." , tags = {"views"})
	@RequestMapping(value = "/admin" , method = RequestMethod.GET)
	public String adminOnly() {
		return "adminOnly";
	}
	
	@RequestMapping(value = "/authentication/denied" , method = RequestMethod.GET)
	public MessageDTO invalidCertification(@CookieValue(value = "refreshToken" , required = false) String refreshToken) {
		if(refreshToken == null) {
			return MessageDTO.builder()
					.code(-1)
					.message("인증되지 않은 사용자")
					.build();
		}
		
		return jwtService.validateRefreshToken(refreshToken);
	}
	
	@RequestMapping(value = "/authorization/denied" , method = RequestMethod.GET)
	public MessageDTO invalidAppropriation() {
		return MessageDTO.builder()
				.code(-1)
				.message("인가되지 않은 사용자")
				.build();
	}
	
}
