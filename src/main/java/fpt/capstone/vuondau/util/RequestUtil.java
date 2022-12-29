package fpt.capstone.vuondau.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.capstone.vuondau.MoodleRepository.MoodleCaller;
import fpt.capstone.vuondau.MoodleRepository.request.MoodleMasterDataRequest;
import fpt.capstone.vuondau.MoodleRepository.response.MoodleLoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RequestUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestUtil.class);
    private final RestTemplateBuilder restTemplateBuilder;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final MoodleCaller s1Caller;

    @Autowired
    public RequestUtil(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper, MoodleCaller s1Caller) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
        this.s1Caller = s1Caller;
    }

    public String getS1Token() {
        MoodleLoginResponse s1LoginResponse = s1Caller.login();
        return s1LoginResponse.getData().get(0).getToken();
    }

    public MoodleMasterDataRequest getIdentifyRequest(String type) {
        MoodleMasterDataRequest s1MasterDataRequest = new MoodleMasterDataRequest();
        String token = getS1Token();
        s1MasterDataRequest.setToken(token);
        s1MasterDataRequest.setType(type);

        return s1MasterDataRequest;
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
