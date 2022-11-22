package fpt.capstone.vuondau.MoodleRepository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import fpt.capstone.vuondau.util.MapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class Caller {

    @Autowired
    RestTemplate restTemplate;

    @Value("${moodle.token}")
    private String token;

    public <RequestType, ResponseType> ResponseType post(String uri, RequestType request, Class<ResponseType> responseTypeClass) {
        String s = restTemplate.postForObject(uri, request, String.class);
        try {
            return MapperUtil.getInstance().getMapper().readValue(s, responseTypeClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <RequestType, ResponseType> ResponseType post(String uri, RequestType request, TypeReference<ResponseType> typeReference) {
        String s = restTemplate.postForObject(uri, request, String.class);
        try {
            return MapperUtil.getInstance().getMapper().readValue(s, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <ResponseType> ResponseType get(String uri, Class<ResponseType> responseTypeClass) {
        return restTemplate.getForObject(uri, responseTypeClass);
    }

    public <RequestType, ResponseType> ResponseType getWithAuthenticationTokeCustom(String uri, RequestType request, TypeReference<ResponseType> typeReference) throws JsonProcessingException {
        HttpHeadersCustom headers = new HttpHeadersCustom();
        //Set Content Type
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String requestAsString = MapperUtil.getInstance().getMapper().writeValueAsString(request);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestAsString, headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        try {
            return MapperUtil.getInstance().getMapper().readValue(response.getBody(), typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <RequestType, ResponseType> ResponseType postWithAuthenticationTokeCustom(String uri, RequestType request, TypeReference<ResponseType> typeReference) throws JsonProcessingException {
        HttpHeadersCustom headers = new HttpHeadersCustom();
        //Set Content Type
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String requestAsString = MapperUtil.getInstance().getMapper().writeValueAsString(request);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestAsString, headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        try {
            return MapperUtil.getInstance().getMapper().readValue(response.getBody(), typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public <RequestType, ResponseType> ResponseType putWithAuthenticationTokeCustom(String uri, RequestType request, TypeReference<ResponseType> typeReference) throws JsonProcessingException {
        HttpHeadersCustom headers = new HttpHeadersCustom();
        //Set Content Type
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String requestAsString = MapperUtil.getInstance().getMapper().writeValueAsString(request);
        HttpEntity<String> httpEntity = new HttpEntity<>(requestAsString, headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, httpEntity, String.class);
        try {
            return MapperUtil.getInstance().getMapper().readValue(response.getBody(), typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
