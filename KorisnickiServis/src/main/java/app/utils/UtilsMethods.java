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
//		headers.add("Authorization", token);

		HttpEntity<Object> entity = new HttpEntity<Object>(null, headers);

		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

		return response;
	}

	public static ResponseEntity<Object> sendPost(String url, Object body) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);

		return response;
	}
}
