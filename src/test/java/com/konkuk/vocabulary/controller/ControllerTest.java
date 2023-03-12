package com.konkuk.vocabulary.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
	
	 @InjectMocks
	 private Controller controller;

	
	MockMvc mockMvc;
    
	@BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
	
	@DisplayName("USER 사용자의 접근 허용")
	@Test
	public void accessUserRole() throws Exception {
		//given
		/*
		List<String> roles = new LinkedList<String>();
		roles.add("USER");
		UserEntity userEntity = UserEntity.builder().id("testId").password("testPassword").roles(roles).build();
		*/
		
		//when
		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/user"));
		
		//then
		MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
		String str = mvcResult.getResponse().getContentAsString();
		
		assertThat(str).isEqualTo("userOnly");
	}
	
}
