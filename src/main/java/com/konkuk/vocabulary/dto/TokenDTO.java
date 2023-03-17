package com.konkuk.vocabulary.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
	int code;
	String id;
	String accessToken;
	String grandType;
	@Builder.Default
	List<String> role = new ArrayList<String>();
}
