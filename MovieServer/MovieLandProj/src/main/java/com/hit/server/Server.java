package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private final int m_port;

    public Server(int port) {
        this.m_port = port;
    }

    public void run() {
        try {
            ServerSocket m_server = new ServerSocket(m_port);
            while (true) {
                Socket clientSocket = m_server.accept();
                HandleRequest clientRequest = new HandleRequest(clientSocket);
                clientRequest.process();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
