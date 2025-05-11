package com.times.logs.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicsConfig {

	/*
	@Bean
	public NewTopic logsTopic() {
	    return TopicBuilder.name("logs-topic")
	        .partitions(3)
	        .replicas(1)
	        .build();
	}
	*/
	
}
