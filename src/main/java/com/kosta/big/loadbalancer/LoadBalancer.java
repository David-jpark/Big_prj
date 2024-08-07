package com.kosta.big.loadbalancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class LoadBalancer {
//Round Robin
    private List<Server> servers;
    private AtomicInteger currentIndex;

    public LoadBalancer(List<Server> servers) {
        this.servers = servers;
        this.currentIndex = new AtomicInteger(0);
    }

    public Server NextServer() {
        if(servers.isEmpty()){
            throw new IllegalStateException("No next server available");
        }
        int index = currentIndex.getAndUpdate(i -> (i+1) % servers.size() );
        return servers.get(index);
    }

    public static void main(String[] args) {
        List<Server> servers = List.of(
        new Server("localhost", 8080),
        new Server("localhost", 8081),
        new Server("localhost", 8082),
        new Server("localhost", 8083)
        );

        LoadBalancer loadBalancer = new LoadBalancer(servers);
        for (int i = 0; i < 10; i++) {
            Server server = loadBalancer.NextServer();
            System.out.println("Forword Server : " + server.getIpAddress());
        }
    }
}
