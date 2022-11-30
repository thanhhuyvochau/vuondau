package fpt.capstone.vuondau.controller;


import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.AccountDetailRequest;
import fpt.capstone.vuondau.entity.request.UploadAvatarRequest;
import fpt.capstone.vuondau.service.IAccountDetailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/account-detail")
public class AccountDetailController {

    private final IAccountDetailService iAccountDetailService;

    public AccountDetailController(IAccountDetailService iAccountDetailService) {
        this.iAccountDetailService = iAccountDetailService;
    }

    @Operation(summary = "Đăng ký làm gia sinh cho hệ thống")
    @PostMapping("/register-tutor")
    public ResponseEntity<ApiResponse<Boolean>> registerTutor(@RequestBody AccountDetailRequest accountDetailRequest) {
        return ResponseEntity.ok(ApiResponse.success(iAccountDetailService.registerTutor(accountDetailRequest)));
    }

}
