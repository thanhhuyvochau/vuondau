package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.ArchetypeTeacherDto;
import fpt.capstone.vuondau.entity.dto.SlotDto;
import fpt.capstone.vuondau.service.IArchetypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/archetype")
public class ArchetypeController {

 private final IArchetypeService iArchetypeService ;


    public ArchetypeController(IArchetypeService iArchetypeService) {
        this.iArchetypeService = iArchetypeService;
    }

    @Operation(summary = "Lấy thời khóa biểu có sẵn của giáo viên")
    @GetMapping("/{teacherId}/archetype")
    public ResponseEntity<ApiResponse<List<ArchetypeTeacherDto>>> getArchetypeOfTeacher(@PathVariable long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(iArchetypeService.getArchetypeOfTeacher(teacherId)));
    }

}
