package fpt.capstone.vuondau.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.MoodleRepository.Request.MoodleCourseDataRequest;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.ClassDetailDto;
import fpt.capstone.vuondau.entity.dto.ClassDto;
import fpt.capstone.vuondau.entity.dto.ClassStudentDto;
import fpt.capstone.vuondau.entity.request.ClassSearchRequest;
import fpt.capstone.vuondau.entity.request.CreateClassRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITimeTableService {


    Long createTimeTableClass(Long classId);
}
