package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.RoleRequest;
import fpt.capstone.vuondau.entity.response.RoleResponse;
import fpt.capstone.vuondau.service.IRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final IRoleService roleService;

    public RoleController(IRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(roleService.getAll()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoleResponse>> create(@RequestBody RoleRequest request) {
        return ResponseEntity.ok(ApiResponse.success(roleService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> update(@RequestBody RoleRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(roleService.update(request, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> create(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(roleService.delete(id)));
    }
}
