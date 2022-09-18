package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.common.ApiResponse;
import fpt.capstone.vuondau.entity.request.AccountRequest;
import fpt.capstone.vuondau.entity.response.AccountTeacherResponse;
import fpt.capstone.vuondau.entity.response.AccountTokenResponse;
import fpt.capstone.vuondau.service.IAccountService;
import fpt.capstone.vuondau.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
public class AccountController {
    private final IAccountService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AccountController(IAccountService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<AccountTokenResponse> login(@RequestBody AccountRequest accountRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(accountRequest.getUsername(), accountRequest.getPassword());
        String token = jwtUtil.generateToken(accountRequest.getUsername());
        return ResponseEntity.ok(new AccountTokenResponse(token, "Login Successful!"));
    }


    @Operation(summary = "Tạo tài khoản cho giáo viên")
    @PostMapping
    public ResponseEntity<ApiResponse<AccountTeacherResponse>> createTeacherAccount(@RequestBody AccountRequest accountRequest) {
        return ResponseEntity.ok(ApiResponse.success(userService.createTeacherAccount(accountRequest)));
    }

}
