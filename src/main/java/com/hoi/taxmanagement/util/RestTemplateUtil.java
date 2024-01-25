package com.hoi.taxmanagement.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RestTemplateUtil {

    private final RestTemplate restTemplate;

    public RestTemplateUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String buildQueryString(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        // Join the parameters into a query string
        return "?" + params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
    }

    public <T> ResponseEntity<T> get(String url, Map<String, String> param, HttpHeaders headers, Class<T> responseType) {
        String uri = url + buildQueryString(param);
        log.info("Request url: {}", uri);
        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), responseType);
    }

    public ResponseEntity<ByteArrayResource> getFile(String url, Map<String, String> param, HttpHeaders headers) {
        String uri = url + buildQueryString(param);
        log.info("Request url: {}", uri);
        return restTemplate.execute(uri, HttpMethod.GET, request -> request.getHeaders().addAll(headers), response -> {
            ByteArrayResource resource = new ByteArrayResource(response.getBody().readAllBytes());
            HttpHeaders responseHeaders = response.getHeaders();
            return new ResponseEntity<>(resource, responseHeaders, response.getStatusCode());
        });
    }



}
