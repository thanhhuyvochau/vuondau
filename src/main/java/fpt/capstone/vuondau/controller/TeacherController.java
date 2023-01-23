package fpt.capstone.vuondau.controller;



import fpt.capstone.vuondau.entity.common.ApiPage;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.AccountEditRequest;
import fpt.capstone.vuondau.entity.request.AccountExistedTeacherRequest;
import fpt.capstone.vuondau.entity.response.AccountDetailResponse;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.service.IAccountService;
import fpt.capstone.vuondau.service.ITeacherService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("api/teachers")
public class TeacherController {
    private final ITeacherService iTeacherService;
    private final IAccountService accountService;

    public TeacherController(ITeacherService iTeacherService, IAccountService accountService) {
        this.iTeacherService = iTeacherService;
        this.accountService = accountService;
    }

    @Operation(summary = "Cập nhật hồ sơ giáo viên")
    @PutMapping("/{id}/account/profile")
    public ResponseEntity<AccountResponse> editProfile(@PathVariable long id, @RequestBody AccountEditRequest accountEditRequest) {
        return ResponseEntity.ok(accountService.editTeacherProfile(id, accountEditRequest));
    }
    @Operation(summary = "Tạo tài khoản cho giáo viên")
    @PostMapping("/account")
    public ResponseEntity<ApiResponse<AccountTeacherResponse>> createTeacherAccount(@RequestBody AccountExistedTeacherRequest accountRequest) {
        return ResponseEntity.ok(ApiResponse.success(accountService.createTeacherAccount(accountRequest)));
    }

    @Operation(summary = "Xem thông tin giáo viên tiêu biểu - đã có account ")
    @GetMapping("")
    public ResponseEntity<ApiPage<AccountDetailResponse>> getAllInfoTeacher(Pageable pageable) {
        return ResponseEntity.ok(accountService.getAllInfoTeacher(pageable));
    }


    @Operation(summary = "Lấy danh sách tất cả các giáo viên")
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('MANAGER','ROOT')")
    public ResponseEntity<ApiPage<AccountDetailResponse>> getAllTeacher(Pageable pageable) {
        return ResponseEntity.ok(iTeacherService.getAllTeacher(pageable));
    }


}
