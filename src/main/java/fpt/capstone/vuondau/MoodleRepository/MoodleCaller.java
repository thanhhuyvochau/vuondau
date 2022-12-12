package fpt.capstone.vuondau.MoodleRepository;

import fpt.capstone.vuondau.MoodleRepository.Response.MoodleLoginResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class MoodleCaller {

    @Value("${moodle.baseUri}")
    private String baseUri;




    private final Caller caller;

    public MoodleCaller(Caller caller) {
        this.caller = caller;
    }

    public MoodleLoginResponse login() {
//        String loginUri = Strings.concat(baseUri, Constants.S1Endpoint.LOGIN);
//        MoodleLoginResponse LOGIN = new MoodleLoginResponse();
//        s1LoginRequest.setUsername(username);
//        s1LoginRequest.setPassword(password);
//        return caller.post(loginUri, s1LoginRequest, S1LoginResponse.class);
        return  null ;
    }

//    public boolean logout(String token) {
//        String logoutUri = Strings.concat(baseUri, Constants.S1Endpoint.LOGOUT);
//        S1LogoutRequest request = new S1LogoutRequest();
//        request.setToken(token);
//        S1LogoutResponse response = caller.post(logoutUri, request, S1LogoutResponse.class);
//        return 0 == response.getStatus();
//    }





}
