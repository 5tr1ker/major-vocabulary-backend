package com.konkuk.vocabulary.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.konkuk.vocabulary.dto.MessageDTO;

@RestControllerAdvice
public class GeneralExceptionHandler {

	/*
	 * 토큰 변조 , 사용자를 찾을 수 없을 때
	 */
	@ExceptionHandler({FalsifyTokenException.class , UsernameNotFoundException.class })
	public MessageDTO handleBadRequest(Exception e) {
		return MessageDTO.builder()
				.code(-1)
				.message(e.getMessage())
				.build();
	}
	
}
