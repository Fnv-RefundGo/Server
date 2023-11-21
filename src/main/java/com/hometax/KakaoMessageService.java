package com.hometax;

import org.springframework.stereotype.Service;

@Service
public class KakaoMessageService {

    public void sendKakaoMessage(String userId, String message) {

        System.out.println("Kakao 메시지 전송: " + message);
    }
}