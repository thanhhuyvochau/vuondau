package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.ClassRequest;
import fpt.capstone.vuondau.entity.response.ClassSubjectResponse;
import fpt.capstone.vuondau.service.ITimeTableService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/timetable")
public class TimeTableController {
    private final ITimeTableService iTimeTableService ;

    public TimeTableController(ITimeTableService iTimeTableService) {
        this.iTimeTableService = iTimeTableService;
    }


    @Operation(summary = "Giáo viên tao lich dạy")
    @PostMapping({"/{classId}"})
    public ResponseEntity<ApiResponse<Long>> createTimeTableClass(@PathVariable Long classId  ) {

        return ResponseEntity.ok(ApiResponse.success(iTimeTableService.createTimeTableClass(classId)));
    }






}
