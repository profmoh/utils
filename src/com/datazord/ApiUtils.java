package com.datazord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.datazord.pojos.ResponseDetails;

public class ApiUtils {

	@SuppressWarnings("unchecked")
	public static <T, R> ResponseDetails<T> doRequest(String headerName, String authorization, R bodyJson,
			MultiValueMap<String, String> headerMap, String url, HttpMethod httpMethod, Class<T> t, MediaType type) throws RestClientException {

		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();

		if(StringUtils.isNotBlank(headerName) && StringUtils.isNotBlank(authorization))
			headers.add(headerName, authorization);

		headers.setContentType(type);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

		if (! CollectionsUtils.isEmptyMap(headerMap))
			headers.addAll(headerMap);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();

		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());

		restTemplate.setMessageConverters(messageConverters);

//		if (StringUtils.isBlank(bodyJson))
		if (bodyJson == null)
			bodyJson = (R) "parameters";

		HttpEntity<R> entity = new HttpEntity<>(bodyJson, headers);

		ResponseEntity<T> responseEntity = restTemplate.exchange(url, httpMethod, entity, t);

		if(responseEntity.getStatusCode().equals(HttpStatus.OK))
			return ResponseDetails.ok().body(responseEntity.getBody());
		else
			return ResponseDetails.internalServerError().build();
	}
}
