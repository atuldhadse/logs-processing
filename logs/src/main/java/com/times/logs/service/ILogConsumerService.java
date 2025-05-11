package com.times.logs.service;

import com.times.logs.dto.LogEvent;

public interface ILogConsumerService {

	public void consume(LogEvent logEvent);

}
