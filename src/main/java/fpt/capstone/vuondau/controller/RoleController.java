package fpt.capstone.vuondau.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.RoleRequest;
import fpt.capstone.vuondau.entity.response.RoleResponse;
import fpt.capstone.vuondau.service.IMoodleService;
import fpt.capstone.vuondau.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final IRoleService roleService;
    private final IMoodleService moodleService;

    public RoleController(IRoleService roleService, IMoodleService moodleService) {
        this.roleService = roleService;
        this.moodleService = moodleService;
    }

    @Operation(description = "Lấy tất cả role")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(roleService.getAll()));
    }

    @Operation(description = "Tạo role")
    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> create(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(ApiResponse.success(roleService.create(request)));
    }

    @Operation(description = "Cập nhật role")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> update(@RequestBody RoleRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(roleService.update(request, id)));
    }

    @Operation(description = "Xóa role")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> create(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(roleService.delete(id)));
    }

    @Operation(description = "Đồng bộ moodle role id qua vuondau role")
    @GetMapping("/synchronize")
    public ResponseEntity<ApiResponse<Boolean>> synchonizeRoleIdFromVuondau() throws JsonProcessingException {
        return ResponseEntity.ok(ApiResponse.success(moodleService.synchronizedRoleFromMoodle()));
    }

}
