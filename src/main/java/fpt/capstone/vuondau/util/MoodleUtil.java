package fpt.capstone.vuondau.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.common.ApiException;
import fpt.capstone.vuondau.moodle.repository.MoodleUserRepository;
import fpt.capstone.vuondau.moodle.response.MoodleUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoodleUtil {
    private final MoodleUserRepository moodleUserRepository;

    public MoodleUtil(MoodleUserRepository moodleUserRepository) {
        this.moodleUserRepository = moodleUserRepository;

    }

    public MoodleUserResponse getMoodleUserIfExist(Account account) throws JsonProcessingException {
        List<MoodleUserResponse> moodleUsers = moodleUserRepository.getUserByUserName(account.getKeycloakUserId());
        if (moodleUsers.size() < 1) {
            throw ApiException.create(HttpStatus.NOT_FOUND).withMessage("You have to update your moodle profile before pay any class!");
        } else if (moodleUsers.size() > 1) {
            throw ApiException.create(HttpStatus.NOT_FOUND).withMessage("Some errors happened, please contact admin for helping!");
        }
        return moodleUsers.get(0);
    }

    public MoodleUserResponse getMoodleUserIfExistByKeycloakId(String keycloakId) throws JsonProcessingException {
        List<MoodleUserResponse> moodleUsers = moodleUserRepository.getUserByUserName(keycloakId);
        if (moodleUsers.size() < 1) {
            throw ApiException.create(HttpStatus.NOT_FOUND).withMessage("You have to update your moodle profile before pay any class!");
        } else if (moodleUsers.size() > 1) {
            throw ApiException.create(HttpStatus.NOT_FOUND).withMessage("Some errors happened, please contact admin for helping!");
        }
        return moodleUsers.get(0);
    }
}
