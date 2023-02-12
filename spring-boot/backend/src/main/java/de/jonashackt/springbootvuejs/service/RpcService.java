package de.jonashackt.springbootvuejs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Lists;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RpcService {
    String sessionId = "";
    OkHttpClient client;

    RpcService() {
        client = new OkHttpClient();
    }

    public String getTorrents() {
        Call call = client.newCall(buildRequest());
        Response response = null;
        try {
            response = call.execute();
            if(response.code() == 409) {
                sessionId = response.header("X-Transmission-Session-Id");
                return getTorrents();
            } else {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    private Request buildRequest() {
        RequestBody body = RequestBody.create(createJson(), MediaType.parse("application/json; charset=utf-8"));

        return new Request.Builder()
                .addHeader("X-Transmission-Session-Id", sessionId)
                .url("http://192.168.86.2:9091/transmission/rpc/")
                .post(body)
                .build();
    }

    private String createJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();

        root.put("method", "torrent-get");
        try {
            ObjectNode arguments = mapper.createObjectNode();
            arguments.withArray("fields").add("id").add("name")
                    .add("totalSize").add("addedDate").add("percentDone")
                    .add("uploadRatio").add("activityDate").add("rateDownload")
                    .add("rateUpload").add("downloadDir").add("status");

            root.set("arguments", arguments);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }
}
