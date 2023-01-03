package fpt.capstone.vuondau.moodle.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import fpt.capstone.vuondau.moodle.config.Caller;
import fpt.capstone.vuondau.moodle.request.GetUserByFieldRequest;
import fpt.capstone.vuondau.moodle.response.MoodleRoleResponse;
import fpt.capstone.vuondau.moodle.response.MoodleUserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MoodleUserRepository extends MoodleBaseRepository {

    public MoodleUserRepository(Caller caller) {
        super(caller);
    }

    // Username of moodle user is id of user in Keycloak
    public List<MoodleUserResponse> getUserByUserName(String username) throws JsonProcessingException { // id in Keycloak
        GetUserByFieldRequest request = new GetUserByFieldRequest();
        request.getValues().add(username);
        TypeReference<List<MoodleUserResponse>> typeReference = new TypeReference<List<MoodleUserResponse>>() {
        };
        return caller.post(getGetUserUrl(), request, typeReference);
    }
}
