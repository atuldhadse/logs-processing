package com.times.logs.service.impl;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.times.logs.dto.LogEvent;
import com.times.logs.service.ILogProducerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LogProducerServiceImpl implements ILogProducerService {

	private static final String TOPIC = "logs-topic";

	@Autowired
	private KafkaTemplate<String, LogEvent> kafkaTemplate;

	@Override
	public void sendLog(LogEvent logEvent) {
		CompletableFuture<SendResult<String, LogEvent>> future = kafkaTemplate.send(TOPIC, logEvent);
		future.whenComplete((result, ex) -> {
			if (!Optional.ofNullable(ex).isPresent()) {
				int partition = result.getRecordMetadata().partition();
				long offset = result.getRecordMetadata().offset();
				log.info("log pushed to kafka at partition {} and offset {}", partition, offset);
			} else {
				log.info("error in pushing log event to kafka");
			}
		});
	}

}
