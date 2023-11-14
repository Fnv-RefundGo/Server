package com.kakao.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class KakaoAPI {

    public String getAccessToken(String code){
        String accessToken = "";
        String refreshToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=48fd83d251fcb965331886c642ce9d1b");
            sb.append("&redirect_uri=http://localhost:3000/api/v1/oauth2/kakao");
            sb.append("&code=ynijSlTQX4D542AX1BTV4Mq85UmeNLNDalEeAzemeui7u9KZdsrlZqtHXpMKKw0fAAABi8zikF5PBWDH3LuH7A");
            //sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("response code = " + responseCode);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";
            while ((line = br.readLine()) != null){
                result += line;
            }
            System.out.println("response body=" + result);

            // JsonParser를 사용하기 위해 JsonNode를 ObjectMapper로 변환
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(result);

            accessToken = jsonNode.get("access_token").asText();
            refreshToken = jsonNode.get("refresh_token").asText();

            br.close();
            bw.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return accessToken;
    }

    public HashMap<String, Object> getUserInfo(String accessToken){
        HashMap<String, Object> userInfo = new HashMap<>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";
        try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");  // GET으로 변경
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine()) != null){
                result += line;
            }
            System.out.println("response body =" + result);

            // JsonParser를 사용하기 위해 JsonNode를 ObjectMapper로 변환
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(result);

            JsonNode properties = jsonNode.get("properties");
            JsonNode kakaoAccount = jsonNode.get("kakao_account");

            String nickname = properties.get("nickname").asText();
            String email = kakaoAccount.get("email").asText();

            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

            br.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return userInfo;
    }

    public void kakaoLogout(String accessToken){
        String reqURL = "https://kapi.kakao.com/v1/user/logout";
        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization","Bearer " + accessToken);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while((line = br.readLine()) != null){
                result += line;
            }
            System.out.println(result);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
