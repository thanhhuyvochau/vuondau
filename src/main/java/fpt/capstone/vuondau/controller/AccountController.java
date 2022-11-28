package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.dto.RequestFormDto;
import fpt.capstone.vuondau.entity.request.AccountEditRequest;
import fpt.capstone.vuondau.entity.request.AccountExistedTeacherRequest;
import fpt.capstone.vuondau.entity.request.AccountRequest;
import fpt.capstone.vuondau.entity.request.UploadAvatarRequest;
import fpt.capstone.vuondau.entity.response.AccountResponse;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.entity.response.AccountTokenResponse;
import fpt.capstone.vuondau.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class AccountController {
    private final IAccountService accountService;

    public AccountController(IAccountService userService) {
        this.accountService = userService;
    }

    @Operation(summary = "Tạo tài khoản cho giáo viên")
    @PostMapping
    public ResponseEntity<ApiResponse<AccountTeacherResponse>> createTeacherAccount(@RequestBody AccountExistedTeacherRequest accountRequest) {
        return ResponseEntity.ok(ApiResponse.success(accountService.createTeacherAccount(accountRequest)));
    }

    @PostMapping("/teacher")
    public ResponseEntity<Account> saveAccount (Account account) {
        return ResponseEntity.ok(accountService.saveAccount(account));
    }


    @GetMapping("/teacher")
    public ResponseEntity<List<Account>> getAccount () {
        return ResponseEntity.ok(accountService.getAccount()) ;
    }

    @Operation(summary = "account upload avatar")
    @PostMapping("/{id}/upload-avatar")
    public ResponseEntity<Boolean> uploadAvatar (@PathVariable long id,  @ModelAttribute UploadAvatarRequest uploadAvatarRequest) throws IOException {
        return ResponseEntity.ok(accountService.uploadAvatar(id, uploadAvatarRequest));
    }

    @Operation(summary = "Giáo viên / học sinh edit profile ")
    @PutMapping("/{id}/edit-profile")
    public ResponseEntity<AccountResponse> editProfile (@PathVariable long id, @RequestBody AccountEditRequest accountEditRequest) throws IOException {
        return ResponseEntity.ok(accountService.editProfile(id, accountEditRequest));
    }

    @Operation(summary = "Xem thông tin giáo viên giáo viên")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getInfoTeacher (@PathVariable long id ) {

        return ResponseEntity.ok(accountService.getInfoTeacher(id)) ;
    }


}
