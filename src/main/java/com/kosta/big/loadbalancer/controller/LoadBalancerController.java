package com.kosta.big.loadbalancer.controller;

import com.kosta.big.loadbalancer.LoadBalancer;
import com.kosta.big.loadbalancer.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoadBalancerController {
    private LoadBalancer loadBalancer;

    public LoadBalancerController(){
        List<Server> servers = List.of(
                new Server ("localhost", 8080),
                new Server ("localhost", 8081),
                new Server ("localhost", 8082),
                new Server ("localhost", 8083)
        );
        this.loadBalancer = new LoadBalancer(servers);
    }

    @GetMapping("/next-server")
    public String ctlNextServer(){
        Server server = loadBalancer.NextServer();
        return "Forword Server : " + server.getIpAddress();
    }

}
