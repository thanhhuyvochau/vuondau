package fpt.capstone.vuondau.util;

import fpt.capstone.vuondau.entity.Voice;
import fpt.capstone.vuondau.entity.common.EGenderType;
import fpt.capstone.vuondau.entity.response.GenderResponse;
import fpt.capstone.vuondau.entity.response.VoiceResponse;
import fpt.capstone.vuondau.moodle.repository.MoodleCourseRepository;
import fpt.capstone.vuondau.repository.ClassLevelRepository;
import fpt.capstone.vuondau.repository.VoiceRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VoiceUtil {
    private static VoiceRepository staticVoiceRepository;


    public VoiceUtil(VoiceRepository voiceRepository) {

        staticVoiceRepository = voiceRepository;

    }

    public static List<VoiceResponse> getVoice() {
        List<VoiceResponse> voiceResponses = new ArrayList<>();
        List<Voice> all = staticVoiceRepository.findAll();
        for (Voice voice : all) {
            voiceResponses.add(ObjectUtil.copyProperties(voice, new VoiceResponse(), VoiceResponse.class));
        }
        return voiceResponses;
    }

}
