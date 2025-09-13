package com.lays.http;

import java.util.HashMap;
import java.util.Map;

public class MainTest {
    public static void main(String[] args) {
        HttpClient client = new HttpClient();

        System.out.println("GET");
        Map<String, String> params = new HashMap<>();
        params.put("userId", "1");
        String getResponse = client.get("https://jsonplaceholder.typicode.com/posts", null, params);
        System.out.println(getResponse);


        System.out.println("\nPOST");
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer b5aa7ff749e6afa2416b7fb90a2cc53d4c2ed88a681fcfce08e5499defb27d68");
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");

        Map<String, String> postData = new HashMap<>();
        postData.put("name", "Laysan");
        postData.put("email", "minlebaeva@gmail.com");
        postData.put("gender", "female");
        postData.put("status", "active");

        String postResponse = client.post("https://gorest.co.in/public/v2/users", headers, postData);
        System.out.println(postResponse);
    }
}