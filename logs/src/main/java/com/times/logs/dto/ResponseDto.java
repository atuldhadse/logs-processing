package com.times.logs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "Response", description = "Response With status code and Message")
public class ResponseDto {

	@Schema(description = "Response Status Code")
	private String statusCode;

	@Schema(description = "Response Status Message")
	private String statusMsg;

}