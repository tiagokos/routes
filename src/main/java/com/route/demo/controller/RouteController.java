package com.route.demo.controller;


import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.route.demo.service.RouteService;

/**
 * Entry point for the application
 * 
 * @author Tiago Kosciuk
 *
 */
@RestController
@Validated
public class RouteController {

	private static Logger LOGGER = LoggerFactory.getLogger(RouteController.class);

	@Autowired
	private RouteService service;
	
	/**
	 * Returns a route between two countries
	 * Validates parameters (origin and destination)
	 * 
	 * @param origin
	 * @param destination
	 * @return
	 */
	@GetMapping("/routing/{origin}/{destination}")
	public ResponseEntity<Route> routing(@PathVariable(value="origin") @Size(min = 3, max = 3) String origin, @PathVariable(value="destination") @Size(min = 3, max = 3) String destination) {
		LOGGER.info("Request received for origin {} and destination {}", origin, destination);
		Route route = service.getRouting(origin, destination);
		HttpStatus status = HttpStatus.OK;
		if (route.getRoute().isEmpty()) {
			status = HttpStatus.BAD_REQUEST;
			route = null;
		}
		return new ResponseEntity<Route>(route, status);
	}
	
}
