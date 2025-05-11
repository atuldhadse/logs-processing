package com.times.logs.service;

import com.times.logs.dto.LogEvent;

public interface ILogProducerService {

	public void sendLog(LogEvent log);

}
