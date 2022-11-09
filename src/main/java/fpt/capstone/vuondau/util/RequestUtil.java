package fpt.capstone.vuondau.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Component
public class RequestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);
    private final RestTemplateBuilder restTemplateBuilder;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RequestUtil(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public <RequestType, ResponseType> ResponseType post(String uri, RequestType request, Class<ResponseType> responseTypeClass) {
        String s = restTemplate.postForObject(uri, request, String.class);
        try {
            return objectMapper.readValue(s, responseTypeClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <RequestType, ResponseType> ResponseType post(String uri, RequestType request, TypeReference<ResponseType> typeReference) {
        String s = restTemplate.postForObject(uri, request, String.class);
        try {
            return objectMapper.readValue(s, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <ResponseType> ResponseType get(String uri, Class<ResponseType> responseTypeClass) {
        return restTemplate.getForObject(uri, responseTypeClass);
    }
}
