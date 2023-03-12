package com.konkuk.vocabulary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
	@Schema(description = "코드")
	int code;
	
	@Schema(description = "메세지")
	String message;
}
