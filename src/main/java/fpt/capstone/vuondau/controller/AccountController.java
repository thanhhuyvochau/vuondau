package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.AccountExistedTeacherRequest;
import fpt.capstone.vuondau.entity.request.AccountRequest;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.entity.response.AccountTokenResponse;
import fpt.capstone.vuondau.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(accountService.saveAccount(account)) ;
    }


    @GetMapping("/teacher")
    public ResponseEntity<List<Account>> getAccount () {
        return ResponseEntity.ok(accountService.getAccount()) ;
    }

}
