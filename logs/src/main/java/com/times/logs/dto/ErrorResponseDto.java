package com.times.logs.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "ErrorResponse", description = "Schema to hold error response information")
public class ErrorResponseDto {

	@Schema(description = "Requested API Path")
	private String apiPath;

	@Schema(description = "Error Code of Request")
	private HttpStatus errorCode;

	@Schema(description = "Error Message of Request")
	private String errorMsg;

	@Schema(description = "Error Time")
	private LocalDateTime errorTime;

}