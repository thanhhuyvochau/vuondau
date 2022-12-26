package fpt.capstone.vuondau.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.TimeTableDto;
import fpt.capstone.vuondau.entity.request.ClassRequest;
import fpt.capstone.vuondau.entity.request.TimeTableRequest;
import fpt.capstone.vuondau.entity.request.TimeTableSearchRequest;
import fpt.capstone.vuondau.entity.response.ClassAttendanceResponse;
import fpt.capstone.vuondau.entity.response.ClassSubjectResponse;
import fpt.capstone.vuondau.service.ITimeTableService;
import io.swagger.v3.oas.annotations.Operation;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmIndexManyToAnyType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/timetable")
public class TimeTableController {
    private final ITimeTableService iTimeTableService ;

    public TimeTableController(ITimeTableService iTimeTableService) {
        this.iTimeTableService = iTimeTableService;
    }


    @Operation(summary = "Giáo viên tao lich dạy")
    @PostMapping({"/{classId}"})
    public ResponseEntity<ApiResponse<Long>> createTimeTableClass(@PathVariable Long classId , Long numberSlot , @RequestBody TimeTableRequest timeTableRequest  ) throws ParseException {
        return ResponseEntity.ok(ApiResponse.success(iTimeTableService.createTimeTableClass(classId, numberSlot, timeTableRequest)));
    }


    @Operation(summary = "GV/Hs Lấy thời khóa biểu của class trong một ngay")
    @GetMapping({"/time-table-day/class"})
    public ResponseEntity<ApiResponse<ApiPage<TimeTableDto>>> getTimeTableInDay(@Nullable  TimeTableSearchRequest timeTableSearchRequest, Pageable pageable)  {
        return ResponseEntity.ok(ApiResponse.success(iTimeTableService.getTimeTableInDay( timeTableSearchRequest,pageable)));
    }

    @Operation(summary = "giao vien/ học sinh xem tất cả thời khoá biểu/điểm danh ")
    @GetMapping("/all-time-table")
    public ResponseEntity<ApiResponse<List<ClassAttendanceResponse>>> accountGetAllTimeTable() throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(iTimeTableService.accountGetAllTimeTable()));
    }


    @Operation(summary = "Admin tao lich dạy")
    @PostMapping({"/{classId}/admin"})
    public ResponseEntity<ApiResponse<Long>> adminCreateTimeTableClass(@PathVariable Long classId , Long numberSlot , @RequestBody TimeTableRequest timeTableRequest  ) throws ParseException {
        return ResponseEntity.ok(ApiResponse.success(iTimeTableService.adminCreateTimeTableClass(classId, numberSlot, timeTableRequest)));
    }


}
