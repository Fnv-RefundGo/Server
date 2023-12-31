package com.hometax;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HomeTaxService {

    @Value("${hometax.api.authorizationurl}")
    private String authorizationUrl;

    @Value("${hometax.api.tokenurl}")
    private String tokenUrl;

    @Value("${hometax.api.resulturl}")
    private String resultUrl;

    private final RestTemplate restTemplate;

    public HomeTaxService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public String getAccessToken(String code) {

        String requestBody = "grant_type=authorization_code&code=" + code;
        String response = restTemplate.postForObject(tokenUrl, requestBody, String.class);

        String accessToken = extractAccessTokenFromResponse(response);

        return accessToken;
    }

    public String handleCallback(String code) {

        String accessToken = getAccessToken(code);

        String resultResponse = restTemplate.getForObject(resultUrl + "?access_token=" + accessToken, String.class);

        String processedResult = parseHomeTaxResult(resultResponse);

        String message;
        if ("success".equals(processedResult)) {
            message = "홈택스 간편인증 및 카카오톡 메시지 전송이 완료되었습니다. 결과: " + processedResult;
        } else {
            message = "홈택스 간편인증 실패. 결과: " + processedResult;
        }

        return message;
    }

    private String extractAccessTokenFromResponse(String response) {

        return "access_token_from_api_response";
    }

    private String parseHomeTaxResult(String resultResponse) {

        if (resultResponse.contains("success")) {
            return "success";
        } else {
            return "error";
        }
    }
}
