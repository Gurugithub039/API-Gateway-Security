package com.guru.apigateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping("/bookFallBack")
    public Mono<String> bookFallBack() {
        return Mono.just("Booking Service is taking too long to respond or is down. Please try again later");
    }
    @RequestMapping("/paymentFallback")
    public Mono<String> paymentServiceFallBack() {
        return Mono.just("Payment Service is taking too long to respond or is down. Please try again later");
    }

    @RequestMapping("/userFallBack")
    public Mono<String> userServiceFallBack() {
        return Mono.just("User Service is taking too long to respond or is down. Please try again later");
    }
    @RequestMapping("/registrationServiceFallBack")
    public Mono<String> registrationServiceFallBack() {
        return Mono.just("Registration Service is taking too long to respond or is down. Please try again later");
    }
}
