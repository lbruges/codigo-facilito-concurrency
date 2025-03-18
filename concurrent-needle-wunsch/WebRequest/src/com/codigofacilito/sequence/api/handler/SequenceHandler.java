package com.codigofacilito.sequence.api.handler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ForkJoinPool;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.codigofacilito.common.props.model.req.WebRequestProperties;
import com.codigofacilito.common.props.reader.req.RequestProperties;
import com.codigofacilito.sequence.api.concurrency.HttpRequestTask;
import com.codigofacilito.sequence.api.service.SequenceService;
import com.codigofacilito.sequence.api.service.SequenceServiceImpl;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;


public class SequenceHandler implements HttpHandler {

    private final SequenceService sequenceService = new SequenceServiceImpl();

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            RequestProperties requestProperties = RequestProperties.getInstance();
            WebRequestProperties webRequestProperties = requestProperties.getWebRequestProperties();

            if ("POST".equals(exchange.getRequestMethod())) {
                ForkJoinPool pool = new ForkJoinPool();

                HttpRequestTask task1 = new HttpRequestTask(webRequestProperties.getSeqAId());
                HttpRequestTask task2 = new HttpRequestTask(webRequestProperties.getSeqBId());

                String response1 = pool.invoke(task1);
                String response2 = pool.invoke(task2);

                pool.shutdown();

                Pattern pattern = Pattern.compile("\"seq\":\"([^\"]+)\"");
                Matcher matcher1 = pattern.matcher(response1);
                Matcher matcher2 = pattern.matcher(response2);

                if (matcher1.find()) {
                    System.out.println("Valor de seq1: " + matcher1.group(1));
                } else {
                    System.out.println("No se encontró el valor de seq.");
                }

                if (matcher2.find()) {
                    System.out.println("Valor de seq2: " + matcher2.group(1));
                } else {
                    System.out.println("No se encontró el valor de seq.");
                }

                sequenceService.process(matcher1.group(1), matcher2.group(1));

                // Enviar respuesta
                exchange.sendResponseHeaders(201, "File created successfully".length());
                OutputStream os = exchange.getResponseBody();
                os.write("File created successfully".getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // Método no permitido
            }
        }



        private String fetchExternalApi(String id) {
            RequestProperties webRequestProperties = RequestProperties.getInstance();
            WebRequestProperties web = webRequestProperties.getWebRequestProperties();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://rest.ensembl.org/sequence/id/"+id+"?content-type=application/json"))
                    .GET()
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                return response.body();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return "{\"error\": \"No se pudo obtener la respuesta\"}";
            }
        }
    }