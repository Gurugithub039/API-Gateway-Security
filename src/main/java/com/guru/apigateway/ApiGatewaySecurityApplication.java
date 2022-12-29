package com.guru.apigateway;

import com.guru.apigateway.config.RedisHashComponent;
import com.guru.apigateway.dto.ApiKey;
import com.guru.apigateway.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableCircuitBreaker
public class ApiGatewaySecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewaySecurityApplication.class, args);
	}


	@Autowired
	private RedisHashComponent redisHashComponent;

	@PostConstruct
	public void initKeysToRedis() {
		List<ApiKey> apiKeys = new ArrayList<>();
		apiKeys.add(new ApiKey("343C-ED0B-4137-B27E", Stream.of(AppConstants.USER_SERVICE_KEY,
				AppConstants.REGISTER_SERVICE_KEY).collect(Collectors.toList())));
		apiKeys.add(new ApiKey("FA48-EF0C-427E-8CCF", Stream.of(AppConstants.REGISTER_SERVICE_KEY)
				.collect(Collectors.toList())));
		List<Object> lists = redisHashComponent.hValues(AppConstants.RECORD_KEY);
		if (lists.isEmpty()) {
			apiKeys.forEach(k -> redisHashComponent.hSet(AppConstants.RECORD_KEY, k.getKey(), k));
		}
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(AppConstants.USER_SERVICE_KEY,
						r -> r.path("/api/user-service/**")
								.filters(f -> f.stripPrefix(2)).uri("http://localhost:8081"))
				.route(AppConstants.REGISTER_SERVICE_KEY,
						r -> r.path("/api/register-service/**")
								.filters(f -> f.stripPrefix(2)).uri("http://localhost:8082"))
				.build();
	}

}
