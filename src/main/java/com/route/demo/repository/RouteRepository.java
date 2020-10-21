package com.route.demo.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class RouteRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(RouteRepository.class);
	
	@Cacheable("countries")
	public List<CountryData> loadCountries() {
		List<CountryData> countries = new ArrayList<>();
		try {
			Collections.addAll(countries, new ObjectMapper().readValue(new File("src/main/resources/countries.json"), CountryData[].class)); 
		} catch (IOException e) {
			LOGGER.error("Error on loading and parsing countries.json", e);
		}
		return countries;
	}
	
}
