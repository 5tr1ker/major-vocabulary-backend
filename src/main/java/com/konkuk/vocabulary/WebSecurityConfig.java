package com.konkuk.vocabulary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.konkuk.vocabulary.oauth.OAuth2Provider;
import com.konkuk.vocabulary.security.JwtAuthenticationFilter;
import com.konkuk.vocabulary.security.JwtTokenProvider;

import jakarta.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";
	private static List<String> clients = Arrays.asList("google", "naver");
	@Resource
	private Environment env;

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		List<ClientRegistration> registrations = clients.stream().map(c -> getRegistration(c))
				.filter(registration -> registration != null).collect(Collectors.toList());
		return new InMemoryClientRegistrationRepository(registrations);
	}

	private ClientRegistration getRegistration(String client) {
		// API Client Id 불러오기
		String clientId = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-id");

		// API Client Id 값이 존재하는지 확인하기
		if (clientId == null) {
			return null;
		}

		// API Client Secret 불러오기
		String clientSecret = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-secret");

		if (client.equals("google")) {
			return OAuth2Provider.GOOGLE.getBuilder(client).clientId(clientId).clientSecret(clientSecret).build();
		}

		if (client.equals("naver")) {
			return OAuth2Provider.NAVER.getBuilder(client).clientId(clientId).clientSecret(clientSecret).build();
		}

		return null;
	}

	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable() // Post 요청 block 제거
			.authorizeHttpRequests() // 해당 메소드 아래는 각 경로에 따른 권한을 지정할 수 있다.
			.requestMatchers("/").permitAll()
				.requestMatchers("/user/**").hasAnyRole("ADMIN" , "USER")
				.requestMatchers("/admin/**").hasRole("ADMIN") // .authenticated()
				.anyRequest().permitAll()	// .requestMatchers("/**").permitAll() 와 같다.
				//.and().formLogin().loginPage("/requestRefreshToken")  로그인된 사용자가 요청을 수행할  필요하다 만약 사용자가 인증되지 않았다면, 스프링 시큐리티 필터는 요청을 잡아내고 사용자를 로그인 페이지로 리다이렉션 해준다.
				// .logoutUrl("/logout") // 로그아웃 url 시 해당 쿠키를 제거
				//.deleteCookies("refreshToken")
				// .logoutSuccessUrl("/")
				
				.and().oauth2Login()
				.clientRegistrationRepository(clientRegistrationRepository())
				.authorizedClientService(authorizedClientService())
				.and().exceptionHandling() // 인증 , 인가 되지 않은 사용자를 탐색
				.authenticationEntryPoint((request, response, authException) -> { // 인증되지 않은 대상
					response.sendRedirect("/invalidCertification");
				})
				.accessDeniedPage("/invalidAppropriation") // 인가되지 않은 대상 
				/* */
		.and()
		.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), // 필터
				UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
