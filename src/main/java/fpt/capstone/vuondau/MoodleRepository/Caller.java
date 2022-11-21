package fpt.capstone.vuondau.MoodleRepository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import fpt.capstone.vuondau.util.MapperUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Caller {


    @Autowired
    RestTemplate restTemplate;



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
}
