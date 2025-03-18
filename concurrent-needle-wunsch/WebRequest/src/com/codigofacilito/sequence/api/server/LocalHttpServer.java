package com.codigofacilito.sequence.api.server;

import com.codigofacilito.sequence.api.handler.SequenceHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;

import java.net.InetSocketAddress;

public class LocalHttpServer {
    public static void startServer() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/sequence", new SequenceHandler());

        server.start();
        System.out.println("Server started http://localhost:8080/api/sequence");
    }


}