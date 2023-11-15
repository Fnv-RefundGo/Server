package com.kakao.app;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@RestController
public class KakaoAuthController {

    private final KakaoAPI kakaoAPI = new KakaoAPI();

    @GetMapping("/api/v1/oauth2/kakao")
    public @ResponseBody HashMap<String, Object> oauth2Kakao(@RequestParam("code") String code) {
        String accessToken = kakaoAPI.getAccessToken(code);
        return kakaoAPI.getUserInfo(accessToken);
    }
}
