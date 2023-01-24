package fpt.capstone.vuondau.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.dto.TimeTableDto;
import fpt.capstone.vuondau.entity.request.TimeTableRequest;
import fpt.capstone.vuondau.entity.request.TimeTableSearchRequest;
import fpt.capstone.vuondau.entity.response.ClassAttendanceResponse;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;

public interface ITimeTableService {


    String createTimeTableClass(Long classId,Long numberSlot ,TimeTableRequest timeTableRequest ) throws ParseException, JsonProcessingException;

    ApiPage<TimeTableDto> getTimeTableInDay(TimeTableSearchRequest timeTableSearchRequest, Pageable pageable) ;

    List<ClassAttendanceResponse> accountGetAllTimeTable();

    Long adminCreateTimeTableClass(Long classId, Long numberSlot, TimeTableRequest timeTableRequest) throws ParseException;

    Long adminUpdateTimeTableClass(Long classId, Long numberSlot, TimeTableRequest timeTableRequest);
}
