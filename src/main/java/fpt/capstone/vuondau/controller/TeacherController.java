package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Role;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.common.EAccountRole;
import fpt.capstone.vuondau.service.ITeacherService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/teachers")
public class TeacherController {
    private final ITeacherService iTeacherService ;

    public TeacherController(ITeacherService iTeacherService) {
        this.iTeacherService = iTeacherService;
    }

    @GetMapping
    @Operation(summary = "Lấy thông tin giáo viên")
    public ResponseEntity<ApiResponse<List<Account>>> getTeacher() {
        return ResponseEntity.ok(ApiResponse.success(iTeacherService.getTeacher()));
    }

}
