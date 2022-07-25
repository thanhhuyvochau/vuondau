package fpt.capstone.vuondau.controller;

import fpt.capstone.vuondau.entity.request.UserRequest;
import fpt.capstone.vuondau.entity.response.UserResponse;
import fpt.capstone.vuondau.service.IUserService;
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
public class UserController {
    private final IUserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserController(IUserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest userRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword());
        String token = jwtUtil.generateToken(userRequest.getUsername());
        return ResponseEntity.ok(new UserResponse(token, "Token generated successfully!"));
    }
}
