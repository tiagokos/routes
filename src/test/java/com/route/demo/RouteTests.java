package com.route.demo;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RouteTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testDefaultScenario() throws Exception {
		this.mockMvc.perform(get("/routing/CZE/ITA")).andDo(print()).andExpect(status().isOk()).andExpect(content().json("{\"route\":[\"CZE\",\"AUT\",\"ITA\"]}"));
	}
	
	@Test
	public void testDefaultScenario2() throws Exception {
		this.mockMvc.perform(get("/routing/ITA/CZE")).andDo(print()).andExpect(status().isOk()).andExpect(content().json("{\"route\":[\"ITA\",\"AUT\",\"CZE\"]}"));
	}
	
	@Test
	public void testError() throws Exception {
		this.mockMvc.perform(get("/routing/CZE/BRA")).andDo(print()).andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testErrorInput() throws Exception {
		this.mockMvc.perform(get("/routing/1234/5678")).andDo(print()).andExpect(status().is5xxServerError());
	}
	
	@Test
	public void testErrorInput2() throws Exception {
		this.mockMvc.perform(get("/routing/")).andDo(print()).andExpect(status().is4xxClientError());
	}
	
}