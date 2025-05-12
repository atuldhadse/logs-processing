package com.times.logs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.times.logs.dto.ErrorResponseDto;
import com.times.logs.dto.LogEvent;
import com.times.logs.service.IElasticsearchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Elasticsearch fetch API", description = "API to search logs")
@RestController
@RequestMapping("/logs")
public class ElasticsearchController {

	@Autowired
	private IElasticsearchService elasticSearchService;

	@Operation(description = "log search API", responses = {
				@ApiResponse(responseCode = "200", description = "HTTP Status Fetch"),
				@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", 
					content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
				) 
			})
	@GetMapping("/search")
	public ResponseEntity<Page<LogEvent>> searchLogs(@RequestParam(required = false) String level,
			@RequestParam(required = false) String keyword, 
			@RequestParam(defaultValue = "0") Integer page, 
			@RequestParam(defaultValue = "10") Integer size) {
		Page<LogEvent> logs = elasticSearchService.searchLogs(level, keyword, 
				PageRequest.of(page, size, Sort.by("timestamp").descending()));
		return ResponseEntity.status(HttpStatus.OK).body(logs);
	}

}
