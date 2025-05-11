package com.times.logs.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.times.logs.dto.LogEvent;

public interface IElasticsearchService {

	public void indexLog(LogEvent logEvent);

	public Page<LogEvent> searchLogs(String level, String keyword, Pageable page);

}
