package com.hometax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HomeController {

    @Autowired
    private KakaoMessageService kakaoMessageService;

    @Autowired
    private HomeTaxService homeTaxService;

    @GetMapping("/auth/homeTax")
    public String homeTaxAuthStart() {
        String authUrl = homeTaxService.getAuthorizationUrl();

        return authUrl;
    }

    @GetMapping("/sendKakaoMessage")
    public ResponseEntity<String> sendKakaoMessage() {
        kakaoMessageService.sendKakaoMessage("userId123", "홈택스 간편인증을 시작합니다.");
        return ResponseEntity.ok("카카오톡 메시지 전송 완료");
    }

    @GetMapping("/auth/homeTax/callback")
    public ResponseEntity<String> homeTaxCallback(@RequestParam("code") String code) {
        String accessToken = homeTaxService.getAccessToken(code);

        kakaoMessageService.sendKakaoMessage("userId123", "홈택스 간편인증이 완료되었습니다.");

        String result = homeTaxService.handleCallback(code);

        return ResponseEntity.ok("홈택스 간편인증 및 카카오톡 메시지 전송이 완료되었습니다. 결과: " + result);
    }
}
