package com.oracle.football.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingAliveController {

    private static int COUNTER = 0;

    record PingResponse(String result) {}

    @GetMapping("/ping")
    public PingResponse serviceAlivePing() {
        return new PingResponse("Service is Alive: %s".formatted(++COUNTER));
    }
}
