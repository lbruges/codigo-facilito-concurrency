package com.codigofacilito.sequence.api.concurrency;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.RecursiveTask;

public class HttpRequestTask extends RecursiveTask<String> {
        private final String param;

        public HttpRequestTask(String param) {
            this.param = param;
        }

        @Override
        protected String compute() {
            try {
                HttpClient client = HttpClient.newHttpClient();
                String baseUrl = "https://rest.ensembl.org/sequence/id/";
                String url = String.format("%s%s", baseUrl, this.param);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url+"?content-type=application/json"))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                return response.body();
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }
    }