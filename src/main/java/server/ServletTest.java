package server;

import com.lays.http.HttpClient;

import java.util.HashMap;
import java.util.Map;

public class ServletTest {
    public static void main(String[] args) {
        HttpClient client = new HttpClient();
        String url = "http://localhost:8080/hello";

        System.out.println("Get");
        String getResponse = client.get(url, null, null);
        System.out.println(getResponse);

        System.out.println("\nPost");
        Map<String, String> postData = new HashMap<>();
        postData.put("message", "My name is Laysan");
        String postResponse = client.post(url, null, postData);
        System.out.println(postResponse);

        System.out.println("\nPut");
        Map<String, String> putData = new HashMap<>();
        putData.put("update", "My name is Laysan, I'm a student");
        String putResponse = client.put(url, null, putData);
        System.out.println(putResponse);
//
//        System.out.println("\nDelete");
//        String deleteResponse = client.delete(url, null, null);
//        System.out.println(deleteResponse);
    }
}
