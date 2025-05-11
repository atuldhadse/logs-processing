package com.times.logs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.times.logs.dto.LogEvent;
import com.times.logs.service.IElasticsearchService;

@RestController
@RequestMapping("/logs")
public class ElasticsearchController {

	@Autowired
	private IElasticsearchService elasticSearchService;

	@GetMapping("/search")
	public ResponseEntity<Page<LogEvent>> searchLogs(@RequestParam(required = false) String level,
			@RequestParam(required = false) String keyword, Pageable page) {
		return ResponseEntity.ok(elasticSearchService.searchLogs(level, keyword, page));
	}

}
