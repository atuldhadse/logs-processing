package com.times.logs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogEvent {

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Instant timestamp;

	private String level;

	private String service;

	private String message;

}