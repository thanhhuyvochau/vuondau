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
    private final MoodleUtil moodleUtil;

    public MoodleUtil(MoodleUserRepository moodleUserRepository, MoodleUtil moodleUtil) {
        this.moodleUserRepository = moodleUserRepository;

        this.moodleUtil = moodleUtil;
    }

    public MoodleUserResponse getMoodleUserIfExist(Account account) throws JsonProcessingException {
        return moodleUtil.getMoodleUserIfExistByKeycloakId(account.getKeycloakUserId());
    }

    public MoodleUserResponse getMoodleUserIfExistByKeycloakId(String keycloakId) throws JsonProcessingException {
        List<MoodleUserResponse> moodleUsers = moodleUserRepository.getUserByUserName(keycloakId);
        if (moodleUsers.size() < 1) {
            StringBuilder message = new StringBuilder("Bạn phải cập nhật hồ sơ theo đường dẫn bên dưới nếu muốn sử dụng tiếp chức năng này: ")
                    .append(moodleUserRepository.getRootUrl());

            throw ApiException.create(HttpStatus.NOT_FOUND).withMessage(message.toString());
        } else if (moodleUsers.size() > 1) {
            throw ApiException.create(HttpStatus.NOT_FOUND).withMessage("Some errors happened, please contact admin for helping!");
        }
        return moodleUsers.get(0);
    }
}
