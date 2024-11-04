package com.hazem.orderservice.config.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazem.orderservice.config.exception.BadRequestException;
import com.hazem.orderservice.config.exception.ResourceNotFoundException;
import com.hazem.orderservice.config.exception.ServerErrorException;
import com.hazem.orderservice.config.shared.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is5xxServerError() ||
                response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is5xxServerError()) {
            throw new ServerErrorException("Please contact administrator system");
        } else if (response.getStatusCode().is4xxClientError()) {
            ObjectMapper mapper = new ObjectMapper();
            ApiResponse apiResponse = mapper.readValue(response.getBody(), ApiResponse.class);
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ResourceNotFoundException(apiResponse.message());
            }
            if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new BadRequestException(apiResponse.message());
            }
            throw new HttpClientErrorException(response.getStatusCode());
        }
    }
}
