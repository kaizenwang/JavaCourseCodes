package com.example.week02;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author kaizen
 * @date 2021/08/15
 */
public class OkHttpUtils {

    private OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {
        OkHttpUtils utils = new OkHttpUtils();
        try {
            System.out.println(utils.run("http://localhost:8801"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String run(String url) throws IOException {
        Request req = new Request.Builder()
                .url(url)
                .build();
        try (Response resp = client.newCall(req).execute()) {
            return resp.body().string();
        }
    }
}
