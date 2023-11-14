package com.kakao.app;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class KakaoAuthController {

    private final String clientId = "88f077af67004ade2b18e854877842f6";
    private final String clientSecret = "OMYPYRFLhm9BOxV1Az6wDkK3TwbNQiyB\n";
    private final String redirectUri = "http://localhost:3000/api/v1/oauth2/kakao";
    private final String tokenUrl = "https://kauth.kakao.com/oauth/token";

    @GetMapping("/api/v1/oauth2/kakao")
    public @ResponseBody String oauth2Kakao(@RequestParam("code") String code) {
        // 토큰 요청을 위한 파라미터 설정
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        String params = "grant_type=authorization_code" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + redirectUri +
                "&code=" + code;

        // 토큰 요청
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(tokenUrl, params, String.class);

        // 실제로는 토큰을 활용해 다양한 작업을 수행하게 됩니다.
        // 여기에서는 단순히 "카카오 인증 완료"를 반환합니다.
        return "카카오 인증 완료";
    }
}