package app.utils;

import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UtilsMethods {

	public static ResponseEntity<Object> sendGet(String url, Map<String, String> headerValues) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		for (Map.Entry<String, String> entry : headerValues.entrySet()) {
			headers.add(entry.getKey(), entry.getValue());
		}
		
		HttpEntity<Object> entity = new HttpEntity<Object>(null, headers);

		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

		return response;
	}

	public static ResponseEntity<Object> sendPost(String url, Object body, Map<String, String> headerValues) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		for (Map.Entry<String, String> entry : headerValues.entrySet()) {
			headers.add(entry.getKey(), entry.getValue());
		}
		headers.add("Content-Type", "application/json");
		
		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);

		return response;
	}
	
	public static ResponseEntity<Boolean> verifyUser(Map<String, String> headerValues, String role) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		for (Map.Entry<String, String> entry : headerValues.entrySet()) {
			headers.add(entry.getKey(), entry.getValue());
		}
		headers.add("Content-Type", "application/json");
		HttpEntity<Object> entity = new HttpEntity<Object>(role, headers);

		ResponseEntity<Boolean> response = restTemplate.exchange("http://localhost:8080/verify", HttpMethod.POST, entity, Boolean.class);

		return response;
	}
}
