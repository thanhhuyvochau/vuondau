package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.request.AccountRequest;
import fpt.capstone.vuondau.entity.response.AccountTokenResponse;
import fpt.capstone.vuondau.service.IAccountService;
import fpt.capstone.vuondau.util.JwtUtil;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
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
}
