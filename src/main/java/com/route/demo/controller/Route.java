package com.route.demo.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Output object for a route between two countries
 * 
 * @author Tiago Kosciuk
 *
 */
public class Route {

	private List<String> route = new ArrayList<>();

	public List<String> getRoute() {
		return route;
	}

	public void setRoute(List<String> route) {
		this.route = route;
	}

}
