package fpt.capstone.vuondau.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.MoodleRepository.Response.MoodleClassResponse;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.request.*;



import java.util.List;

public interface IClassService {


    Boolean teacherRequestCreateClass(Long teacherId, CreateClassRequest createClassRequest) throws JsonProcessingException;

    Boolean synchronizedClassToMoodle( MoodleCourseDataRequest moodleCourseDataRequest) throws JsonProcessingException;
}
