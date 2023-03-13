package com.konkuk.vocabulary.controller;

import javax.security.auth.login.AccountException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.konkuk.vocabulary.dto.MessageDTO;
import com.konkuk.vocabulary.dto.TokenDTO;
import com.konkuk.vocabulary.dto.UserDTO;
import com.konkuk.vocabulary.service.UserService;


@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	public TokenDTO loginUser(@RequestBody UserDTO userDTO) throws AccountException {
		return userService.findId(userDTO);
	}
	
	@RequestMapping(value = "/register" , method = RequestMethod.POST)
	public MessageDTO createUser(@RequestBody UserDTO userDTO) throws AccountException {
		return userService.createId(userDTO);
	}
}
