package com.passionPay.passionPayBackEnd.auth.controller;


import com.passionPay.passionPayBackEnd.member.dto.MemberRequestDto;
import com.passionPay.passionPayBackEnd.member.dto.MemberResponseDto;
import com.passionPay.passionPayBackEnd.auth.dto.TokenDto;
import com.passionPay.passionPayBackEnd.auth.dto.TokenRequestDto;
import com.passionPay.passionPayBackEnd.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @GetMapping("/test")
    public String test() {
        return "hello, test";
    }
}
