package com.times.logs.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.times.logs.dto.LogEvent;
import com.times.logs.service.ILogConsumerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LogConsumerServiceImpl implements ILogConsumerService {

	@Override
	@KafkaListener(topics = "logs-topic", groupId = "log-consumers", containerFactory = "logKafkaListenerContainerFactory")
	public void consume(LogEvent logEvent) {
		log.info("event consumed by the consumer {}", logEvent.toString());
	}

}
