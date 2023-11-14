package com.kakao.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/api/v1/oauth2/kakao")
    public @ResponseBody String oauth2Kakao() {

        return "카카오 인증 완료";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {
        //POST방식으로 key=value 데이터를 요청(카카오톡으로)
        RestTemplate rt = new RestTemplate();

        return "카카오 인증 완료 : 코드값: "+code;
    }

    @GetMapping("/auth/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }
}
