package com.xische.exchangerate.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

@Configuration
public class FeignClientConfig {

    // Set the Feign logging level (optional but helpful for debugging)
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;  // Can be NONE, BASIC, HEADERS, or FULL
    }

    // Configure Feign client timeout
    @Bean
    public ClientHttpRequestFactory feignClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);  // 5 seconds connection timeout
        factory.setReadTimeout(5000);     // 5 seconds read timeout
        return factory;
    }
}
