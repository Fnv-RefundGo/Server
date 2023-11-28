package com.scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import java.io.IOException;


    public class scraping {
        public static void main(String[] args) {

            final String url = "https://www.hometax.go.kr/";

            try {
                Connection connection = Jsoup.connect(url);
                Document document = connection.get();

            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
