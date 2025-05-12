package com.times.logs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.times.logs.constants.LogsConstants;
import com.times.logs.dto.ErrorResponseDto;
import com.times.logs.dto.LogEvent;
import com.times.logs.dto.ResponseDto;
import com.times.logs.service.ILogProducerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Post API for logs", description = "Post API to push log events to kafka")
@RestController
@Validated
@RequestMapping(path = "/logs", produces = { MediaType.APPLICATION_JSON_VALUE })
public class LogsController {

	@Autowired
	private ILogProducerService producer;

	@Operation(description = "Logs push API", responses = {
			@ApiResponse(responseCode = "201", description = "HTTP Status Created"),
			@ApiResponse(responseCode = "400", description = "Bad Request"),
			@ApiResponse(responseCode = "500", 
					description = "HTTP Status Internal Server Error", 
					content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
				) 
			})
	@PostMapping("/push")
	public ResponseEntity<ResponseDto> createLog(@Valid @RequestBody LogEvent logEvent) {
		producer.sendLog(logEvent);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(LogsConstants.STATUS_201, LogsConstants.MESSAGE_201));
	}

}
