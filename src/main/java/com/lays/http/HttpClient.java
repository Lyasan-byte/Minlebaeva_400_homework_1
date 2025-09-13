package com.lays.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClient implements IHttpClient {
    @Override
    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        try {
            String getUrl = appendParamsToUrl(url, params);
            URL obj = new URL(getUrl);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            setHeaders(connection, headers);

            return readResponse(connection);
        } catch (IOException e) {
            throw new RuntimeException("GET request failed", e);
        }
    }

    @Override
    public String post(String url, Map<String, String> headers, Map<String, String> data) {
        return sendRequest(url, "POST", headers, data);
    }

    @Override
    public String put(String url, Map<String, String> headers, Map<String, String> data) {
        return sendRequest(url, "PUT", headers, data);
    }

    @Override
    public String delete(String url, Map<String, String> headers, Map<String, String> data) {
        return sendRequest(url, "DELETE", headers, data);
    }

    // for post, put, delete
    private String sendRequest(String url, String method, Map<String, String> headers, Map<String, String> data) {
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod(method);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);

            setHeaders(connection, headers);

            if (data != null && !data.isEmpty()) {
                String jsonBody = mapToJson(data);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }
            return readResponse(connection);

        } catch (IOException e) {
            throw new RuntimeException(method + " request failed", e);
        }
    }

    private void setHeaders(HttpURLConnection connection, Map<String, String> headers) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    private String appendParamsToUrl(String url, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        sb.append(url.contains("?") ? "&" : "?");
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (!first) sb.append("&");
            sb.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            sb.append("=");
            sb.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            first = false;
        }
        return sb.toString();
    }

    private String mapToJson(Map<String, String> data) {
        if (data == null || data.isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            if (!first) sb.append(",");
            sb.append("\"").append(entry.getKey()).append("\":\"")
                    .append(entry.getValue().replace("\"", "\\\"")).append("\"");
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    private static String readResponse(HttpURLConnection connection) {
        if (connection == null) {
            return null;
        } else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder content = new StringBuilder();
                String input;
                while ((input = reader.readLine()) != null) {
                    content.append(input);
                }
                return content.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}