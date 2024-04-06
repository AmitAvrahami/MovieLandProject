package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;

public class ClientRequest {

    private Socket m_someClient;
    private Gson gson = new GsonBuilder().create();
    public ClientRequest(Socket someClient) {
        this.m_someClient = someClient;
    }

    public void process() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(m_someClient.getInputStream()));
            String gsonFormat = reader.readLine();
            Request request = gson.fromJson(gsonFormat , Request.class);
            String action = request.getAction();
            Map<String,Object> body = request.getBody();
            makeAction(action, body);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeAction(String action, Map<String, Object> body) {
       //TODO : make here a switch case , case is the action type
    }
}
