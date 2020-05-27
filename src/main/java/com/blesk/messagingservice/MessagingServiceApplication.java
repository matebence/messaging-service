package com.blesk.messagingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@EnableDiscoveryClient
@SpringBootApplication
@EnableWebSocketMessageBroker
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class MessagingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingServiceApplication.class, args);
	}
}