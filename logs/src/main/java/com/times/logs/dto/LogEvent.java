package com.times.logs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogEvent {

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotNull(message = "timestamp can't be null")
	private Instant timestamp;

	@NotBlank(message = "level can't be blank or null")
	@Schema(description = "level", examples = { "ERROR", "INFO" })
	private String level;

	@NotBlank(message = "service can't be blank or null")
	@Schema(description = "service", example = "user-service")
	private String service;

	@NotBlank(message = "message can't be blank or null")
	@Schema(description = "log message", example = "User login successful")
	private String message;

}