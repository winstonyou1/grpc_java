package com.yhs.server;

import com.yhs.service.HelloServiceImpl;


import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerBuilder serverBuilder = ServerBuilder.forPort(8082);
        serverBuilder.addService(new HelloServiceImpl());
        Server server = serverBuilder.build();
        server.start();
        server.awaitTermination();

    }
}
