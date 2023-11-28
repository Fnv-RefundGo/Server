package com.comwel;

import com.hometax.KakaoMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/comwel")
public class CwController {

    private final KakaoMessageService kakaoMessageService;
    private final ComwelService comwelService;

    @Autowired
    public CwController(KakaoMessageService kakaoMessageService, ComwelService comwelService) {
        this.kakaoMessageService = kakaoMessageService;
        this.comwelService = comwelService;
    }

    @GetMapping("/auth/comeWel")
    public String comeWelAuthStart() {
        String authUrl = comwelService.getAuthorizationUrl();
        return authUrl;
    }

    @GetMapping("/sendKakaoMessage")
    public ResponseEntity<String> sendKakaoMessage() {
        kakaoMessageService.sendKakaoMessage("userId123", "홈택스 간편인증을 시작합니다.");
        return ResponseEntity.ok("카카오톡 메시지 전송 완료");
    }

    @GetMapping("/auth/comeWel/callback")
    public ResponseEntity<String> comeWelCallback(@RequestParam("code") String code) {
        String accessToken = comwelService.getAccessToken(code);
        kakaoMessageService.sendKakaoMessage("userId123", "홈택스 간편인증이 완료되었습니다.");
        String result = comwelService.handleCallback(code);
        return ResponseEntity.ok("홈택스 간편인증 및 카카오톡 메시지 전송이 완료되었습니다. 결과: " + result);
    }
}
