package com.hazem.orderservice.config.app;

import com.hazem.orderservice.config.shared.ApiResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class HttpUtils {


    private HttpUtils() {
    }

    public static <DTA> ApiResponse execute(DTA data, RestTemplate restTemplate, String url, HttpMethod httpMethod) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DTA> entity = new HttpEntity<>(data, headers);

        ResponseEntity<ApiResponse> exchange = restTemplate.exchange(
                url,
                httpMethod,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );
        return exchange.getBody();
    }

}
