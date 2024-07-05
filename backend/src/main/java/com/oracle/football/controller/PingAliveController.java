package com.oracle.football.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingAliveController {

  private int counter = 0;
  private static final Logger logger = LoggerFactory.getLogger(PingAliveController.class);

  record PingResponse(String result) {

  }

  @GetMapping("/ping")
  public PingResponse serviceAlivePing() {
    logger.info("Received ping request");
    String response = "Service is Alive: %s".formatted(++counter);
    logger.info("Returning response: {}", response);
    return new PingResponse(response);
  }
}
