package com.codigofacilito.sequence.api.handler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import com.codigofacilito.common.props.model.req.WebRequestProperties;
import com.codigofacilito.common.props.reader.req.RequestProperties;
import com.codigofacilito.sequence.api.client.SequenceWebClient;
import com.codigofacilito.sequence.api.client.impl.SequenceWebClientImpl;
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

            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1); // Method not allowed
                return;
            }

            ExecutorService executor = Executors.newFixedThreadPool(2);

            String seqAId = webRequestProperties.getSeqAId();
            String seqBId = webRequestProperties.getSeqBId();

            SequenceWebClient client = new SequenceWebClientImpl();

            CompletableFuture<String>[] seqFutures = Stream.of(seqAId, seqBId)
                    .map(seq -> CompletableFuture.supplyAsync(() -> client.getSequence(webRequestProperties, seq), executor))
                    .toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(seqFutures);

            executor.shutdown();

            String[] sequences = Stream.of(seqFutures)
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new RuntimeException("Unable to process future", e);
                        }
                    })
                    .toArray(String[]::new);

            sequenceService.process(sequences[0], sequences[1]);

            // Send response
            exchange.sendResponseHeaders(201, "File created successfully".length());

            try (OutputStream os = exchange.getResponseBody()) {
                os.write("File created successfully".getBytes());
            }
        }

}