package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.ClassRequest;
import fpt.capstone.vuondau.entity.request.CreateClassRequest;
import fpt.capstone.vuondau.entity.response.ClassSubjectResponse;
import fpt.capstone.vuondau.service.IClassService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/class")
public class ClassController {

    private final IClassService iClassService ;

    public ClassController(IClassService iClassService) {
        this.iClassService = iClassService;
    }


    @Operation(summary = "Giáo viên quest tao class + subject - course")
    @PostMapping({"/{teacherId}"})
    public ResponseEntity<ApiResponse<Boolean>> teacherRequestCreateClass(@PathVariable Long teacherId , @RequestBody CreateClassRequest createClassRequest ) {

        return ResponseEntity.ok(ApiResponse.success(iClassService.teacherRequestCreateClass(teacherId, createClassRequest)));

    }

}
