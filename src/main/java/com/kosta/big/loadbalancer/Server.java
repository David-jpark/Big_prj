package com.kosta.big.loadbalancer;

public class Server {
    private String ipAddress;
    private int port;

    public Server() {

    }
    public Server(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress + ":" + port;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.ipAddress = "127.0.0.1";
        server.port = 8080;
        System.out.println(server.ipAddress + ":" + server.port);

    }
}
