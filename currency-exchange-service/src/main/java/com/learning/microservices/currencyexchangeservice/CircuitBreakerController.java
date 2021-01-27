package com.learning.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	@GetMapping("/sample-api")
	//@Retry(name = "sample-api" , fallbackMethod = "hardCodedResponse")
	@CircuitBreaker(name = "defalut" , fallbackMethod = "hardCodedResponse")
	@RateLimiter(name = "default")
	public String sampleAPI() {
		logger.info("Sample api call received");
		new RestTemplate().getForEntity("http://localhost:8080/getNewValue", String.class);
		return "Sample API";
	}
	
	public String hardCodedResponse(Exception ex) {
		return "Fallback Response";
	}

}
