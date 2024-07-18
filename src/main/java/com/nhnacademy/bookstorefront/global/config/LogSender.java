package com.nhnacademy.bookstorefront.global.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogSender {
	private final RestTemplate restTemplate;

	public void sendLog2(String message) {
		Map<String, Object> logData = new HashMap<>();
		logData.put("projectName", "Xyx7DoyszcG66ULx");
		logData.put("projectVersion", "1.0.0");
		logData.put("logVersion", "v2");
		logData.put("body", message);
		logData.put("logSource", "dev");
		logData.put("logType", "log");
		logData.put("host", "localhost");

		String url = "https://api-logncrash.nhncloudservice.com/v2/log";

		try {
			restTemplate.postForEntity(url, logData, String.class);
		} catch (Exception e) {
		}
	}
}

@RestController
class LogController2 {
	private static final Logger logger = LoggerFactory.getLogger(LogController2.class);
	private final LogSender logSender;

	public LogController2(LogSender logSender) {
		this.logSender = logSender;
	}

	@GetMapping("/send-log1")
	public void sendLog1(@RequestParam String message) {
		logger.info(message);
	}

	@GetMapping("/send-log2")
	public String sendLog2(@RequestParam String message) {
		logSender.sendLog2(message);
		return "Log sent2!";
	}
}

