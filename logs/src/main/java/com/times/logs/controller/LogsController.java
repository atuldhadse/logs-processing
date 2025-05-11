package com.times.logs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.times.logs.dto.LogEvent;
import com.times.logs.service.ILogProducerService;

@RestController
@RequestMapping("/logs")
public class LogsController {

	@Autowired
	private ILogProducerService producer;

	@PostMapping
	public ResponseEntity<String> createLog(@RequestBody LogEvent logEvent) {
		producer.sendLog(logEvent);
		return new ResponseEntity<>("Log submitted successfully", HttpStatus.CREATED);
	}

}
