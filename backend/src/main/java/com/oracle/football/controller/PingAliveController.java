package com.oracle.football.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling ping requests to check if the service is alive.
 */
@RestController
public class PingAliveController {

  private int counter = 0;
  private static final Logger logger = LoggerFactory.getLogger(PingAliveController.class);

  record PingResponse(String result) {

  }
  /**
   * Handles the ping request and returns a response indicating that the service is alive.
   *
   * @return a response entity containing the ping response
   */
  @Operation(summary = "Ping service", description = "Handles the ping request and returns a response indicating that the service is alive.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Service is alive"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/ping")
  public PingResponse serviceAlivePing() {
    logger.info("Received ping request");
    String response = "Service is Alive: %s".formatted(++counter);
    logger.info("Returning response: {}", response);
    return new PingResponse(response);
  }
}
