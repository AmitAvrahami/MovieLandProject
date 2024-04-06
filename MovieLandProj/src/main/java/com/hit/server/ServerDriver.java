package com.hit.server;

public class ServerDriver extends Thread {
    public static void main(String[] args) {

        Server server = new Server(34567);
        new Thread(server).start();
    }
}
