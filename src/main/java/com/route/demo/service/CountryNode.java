package com.route.demo.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents one node (country) of the graph
 * 
 * @author Tiago Kosciuk
 *
 */
public class CountryNode {

	private String cca3;
	private Map<CountryNode, Integer> borders = new HashMap<>();

	private Integer distance = Integer.MAX_VALUE;

	private List<CountryNode> shortestPathFromOrigin = new LinkedList<>();

	public void addBorderCountry(CountryNode destination, int distance) {
		borders.put(destination, distance);
	}

	public CountryNode(String cca3) {
		this.cca3 = cca3;
	}

	public String getCca3() {
		return cca3;
	}

	public void setCca3(String cca3) {
		this.cca3 = cca3;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Map<CountryNode, Integer> getBorders() {
		return borders;
	}

	public void setBorders(Map<CountryNode, Integer> borders) {
		this.borders = borders;
	}

	public List<CountryNode> getShortestPathFromOrigin() {
		return shortestPathFromOrigin;
	}

	public void setShortestPathFromOrigin(List<CountryNode> shortestPathFromOrigin) {
		this.shortestPathFromOrigin = shortestPathFromOrigin;
	}

}