package com.konkuk.vocabulary.service;

import java.util.Collections;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.konkuk.vocabulary.dto.MessageDTO;
import com.konkuk.vocabulary.dto.TokenDTO;
import com.konkuk.vocabulary.dto.UserDTO;
import com.konkuk.vocabulary.entity.Token;
import com.konkuk.vocabulary.entity.UserEntity;
import com.konkuk.vocabulary.repository.UserRepository;
import com.konkuk.vocabulary.security.JwtTokenProvider;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private JwtService jwtService;
	
	public MessageDTO createId(UserDTO userDTO) throws AccountException {
		UserEntity findUserEntity = userRepository.findById(userDTO.getId());
		if(findUserEntity != null)
			throw new AccountException("이미 존재하는 계정입니다.");
		
		UserEntity userEntity = UserEntity.builder()
				.id(userDTO.getId())
				.password(userDTO.getPassword())
				.roles(Collections.singletonList("ROLE_USER"))
				.build();
		
		userRepository.save(userEntity);
		
		return MessageDTO.builder()
				.code(1)
				.message("성공적으로 가입이 완료되었습니다.")
				.build();
	}
	
	public TokenDTO findId(UserDTO userDTO) throws AccountException {
		UserEntity findUserEntity = userRepository.findById(userDTO.getId());
		if(findUserEntity == null) {
			throw new AccountException("해당 계정은 존재하지 않습니다.");			
		}
		
		if(!userDTO.getPassword().equals(findUserEntity.getPassword())) {
			throw new AccountException("비밀번호가 일치하지 않습니다.");
		}
		
		Token tokenDTO = jwtTokenProvider.createAccessToken(findUserEntity.getUsername(), findUserEntity.getRoles());
		jwtService.login(tokenDTO);
		
		return TokenDTO.builder()
				.code(1)
				.id(userDTO.getId())
				.accessToken(tokenDTO.getAccessToken())
				.grandType(tokenDTO.getGrantType())
				.build();
	}
	
}
