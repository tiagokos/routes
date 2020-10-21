package com.route.demo.repository;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Country object mapped from JSON
 * 
 * @author Tiago Kosciuk
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryData {

	private String cca3;

	private Set<String> borders;

	public String getCca3() {
		return cca3;
	}

	public void setCca3(String cca3) {
		this.cca3 = cca3;
	}

	public Set<String> getBorders() {
		return borders;
	}

	public void setBorders(Set<String> borders) {
		this.borders = borders;
	}

}