package com.konkuk.vocabulary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.konkuk.vocabulary.dto.MessageDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Controller~!~" , description = "Controller API Document")
public class Controller {
	
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
	
	@Operation(summary = "권한 없음" , description = "테스트용 API 입니다." , tags = {"views"})
	@RequestMapping(value = "/requestRefreshToken" , method = RequestMethod.GET)
	public String requestRefreshToken() {
		return "403 Error";
	}
	
	@RequestMapping(value = "/invalidCertification" , method = RequestMethod.GET)
	public MessageDTO invalidCertification() {
		return MessageDTO.builder()
			.code(-1)
			.message("인증되지 않은 사용자")
			.build();
	}
	
	@RequestMapping(value = "/invalidAppropriation" , method = RequestMethod.GET)
	public MessageDTO invalidAppropriation() {
		return MessageDTO.builder()
				.code(-1)
				.message("인가되지 않은 사용자")
				.build();
	}
	
}
