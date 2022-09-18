package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.ClassTypeRequest;
import fpt.capstone.vuondau.entity.response.ClassTypeResponse;
import fpt.capstone.vuondau.service.IClassTypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-types")
public class ClassTypeController {
    private final IClassTypeService IClassTypeService;

    public ClassTypeController(IClassTypeService IClassTypeService) {
        this.IClassTypeService = IClassTypeService;
    }

    @Operation(description = "Lấy tất cả loại lớp")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ClassTypeResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(IClassTypeService.getAll()));
    }
    @Operation(description = "Tạo loại lớp")
    @PostMapping
    public ResponseEntity<ApiResponse<ClassTypeResponse>> create(@RequestBody ClassTypeRequest classTypeRequest) {
        return ResponseEntity.ok(ApiResponse.success(IClassTypeService.create(classTypeRequest)));
    }
    @Operation(description = "Cập nhật lọai lớp")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassTypeResponse>> update(@RequestBody ClassTypeRequest classTypeRequest, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(IClassTypeService.update(classTypeRequest, id)));
    }
    @Operation(description = "Xóa loại lớp")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(IClassTypeService.delete(id)));
    }
}
