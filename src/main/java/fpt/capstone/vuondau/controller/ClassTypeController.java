package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.ClassTypeRequest;
import fpt.capstone.vuondau.entity.response.ClassTypeResponse;
import fpt.capstone.vuondau.service.IClassTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-type")
public class ClassTypeController {
    private final IClassTypeService IClassTypeService;

    public ClassTypeController(IClassTypeService IClassTypeService) {
        this.IClassTypeService = IClassTypeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClassTypeResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(IClassTypeService.getAll()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClassTypeResponse>> create(@RequestBody ClassTypeRequest classTypeRequest) {
        return ResponseEntity.ok(ApiResponse.success(IClassTypeService.create(classTypeRequest)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClassTypeResponse>> update(@RequestBody ClassTypeRequest classTypeRequest, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(IClassTypeService.update(classTypeRequest, id)));
    }

    @DeleteMapping("/{id}")
    @GetMapping
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(IClassTypeService.delete(id)));
    }
}
