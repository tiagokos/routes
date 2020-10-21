package com.route.demo.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.route.demo.controller.Route;
import com.route.demo.repository.CountryData;
import com.route.demo.repository.RouteRepository;

/**
 * Main service responsible for calculating the distances and pulling data from repository
 * 
 * @author Tiago Kosciuk
 *
 */
@Service
public class RouteService {

	private static Logger LOGGER = LoggerFactory.getLogger(RouteService.class);

	private Map<String, CountryNode> countryNodesMap;

	@Autowired
	private RouteRepository repository;

	public Route getRouting(String origin, String destination) {
		Route route = new Route();
		if (origin.equalsIgnoreCase(destination)) {
			route.getRoute().add(origin);
		} else {
			// Load data from repository (countries.json)
			List<CountryData> countries = repository.loadCountries();
			
			// Build initial countries graph
			CountryGraph graph = buildCountriesGraph(countries, origin);

			// Update graph with paths 
			graph = calculateDistances(graph, countryNodesMap.get(origin));

			// Get destination node and verify shortest path
			CountryNode destinationNode = countryNodesMap.get(destination);
			if (!destinationNode.getShortestPathFromOrigin().isEmpty()) {
				for (CountryNode countryNode : destinationNode.getShortestPathFromOrigin()) {
					route.getRoute().add(countryNode.getCca3());
				}
				route.getRoute().add(destinationNode.getCca3());
			}
		}		
		return route;
	}

	/**
	 * Build graph structure for countries and its distances
	 * 
	 * @param countries 
	 * @return
	 */
	private CountryGraph buildCountriesGraph(List<CountryData> countries, String origin) {
		countryNodesMap = new HashMap<>();
		CountryGraph countryGraph = new CountryGraph();
		Set<CountryNode> countryNodes = new HashSet<>();

		for (CountryData country : countries) {
			CountryNode node = countryNodesMap.get(country.getCca3());
			if (node == null) {
				node = new CountryNode(country.getCca3());
				countryNodesMap.put(country.getCca3(), node);
			}

			for (String border : country.getBorders()) {
				CountryNode borderNode = countryNodesMap.get(border);
				if (borderNode == null) {
					borderNode = new CountryNode(border);
					countryNodesMap.put(border, borderNode);
				}
				node.addBorderCountry(borderNode, 1);
			}
			countryNodes.add(node);
		}

		countryGraph.setCountryNodes(countryNodes);
		return countryGraph;
	}

	/**
	 * Calculate distances
	 * Algorithm adapted from https://www.baeldung.com/java-dijkstra
	 * 
	 * @param countryGraph
	 * @param origin
	 * @return
	 */
	private CountryGraph calculateDistances(CountryGraph countryGraph, CountryNode origin) {
		origin.setDistance(0);

		Set<CountryNode> settledNodes = new HashSet<>();
		Set<CountryNode> unsettledNodes = new HashSet<>();

		unsettledNodes.add(origin);

		while (unsettledNodes.size() != 0) {
			CountryNode currentNode = getLowestDistanceNode(unsettledNodes);
			unsettledNodes.remove(currentNode);
			for (Entry<CountryNode, Integer> adjacencyPair : currentNode.getBorders().entrySet()) {
				CountryNode adjacentNode = adjacencyPair.getKey();
				if (!settledNodes.contains(adjacentNode)) {
					calculateMinimumDistance(adjacentNode, currentNode);
					unsettledNodes.add(adjacentNode);
				}
			}
			settledNodes.add(currentNode);
		}
		return countryGraph;
	}

	/**
	 * Find closer country
	 * 
	 * @param unsettledNodes
	 * @return
	 */
	private CountryNode getLowestDistanceNode(Set<CountryNode> unsettledNodes) {
		CountryNode lowestDistanceNode = null;
		int lowestDistance = Integer.MAX_VALUE;
		for (CountryNode countryNode : unsettledNodes) {
			int distance = countryNode.getDistance();
			if (distance < lowestDistance) {
				lowestDistance = distance;
				lowestDistanceNode = countryNode;
			}
		}
		return lowestDistanceNode;
	}

	/**
	 * Find minimum distance between two countries
	 * 
	 * @param evaluationNode
	 * @param sourceNode
	 */
	private void calculateMinimumDistance(CountryNode evaluationNode, CountryNode sourceNode) {
		Integer sourceDistance = sourceNode.getDistance();
		if (sourceDistance + 1 < evaluationNode.getDistance()) {
			evaluationNode.setDistance(sourceDistance + 1);
			LinkedList<CountryNode> shortestPath = new LinkedList<>(sourceNode.getShortestPathFromOrigin());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPathFromOrigin(shortestPath);
		}
	}

}
