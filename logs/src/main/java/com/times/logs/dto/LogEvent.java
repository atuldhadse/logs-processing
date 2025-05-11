package com.times.logs.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class LogEvent {

	private Instant timestamp;
	private String level;
	private String service;
	private String message;

}