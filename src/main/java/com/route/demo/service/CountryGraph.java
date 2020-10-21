package com.route.demo.service;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents whole graph of countries
 * 
 * @author Tiago Kosciuk
 *
 */
public class CountryGraph {

	private Set<CountryNode> countryNodes = new HashSet<>();

	public void addCountryNode(CountryNode countryNode) {
		countryNodes.add(countryNode);
	}

	public Set<CountryNode> getCountryNodes() {
		return countryNodes;
	}

	public void setCountryNodes(Set<CountryNode> countryNodes) {
		this.countryNodes = countryNodes;
	}

}