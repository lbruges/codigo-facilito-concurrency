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

/**
 * Sequence handler for a simple server that receives an empty request and process the sequence ids specified in the
 * request.properties file.
 */
public class SequenceHandler implements HttpHandler {

    private static final SequenceWebClient WEB_CLIENT = new SequenceWebClientImpl();

    private final SequenceService sequenceService = new SequenceServiceImpl();

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // POST method allowed only
            if (!"POST".equals(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1); // Method not allowed
                return;
            }

            RequestProperties requestProperties = RequestProperties.getInstance();
            WebRequestProperties webRequestProperties = requestProperties.getWebRequestProperties();

            ExecutorService executor = Executors.newFixedThreadPool(2); // 2 threads, one per sequence

            // Obtain sequence ids to query
            String seqAId = webRequestProperties.getSeqAId();
            String seqBId = webRequestProperties.getSeqBId();

            // Create futures that will handle web client invocation based on the URL in the properties object
            CompletableFuture<String>[] seqFutures = Stream.of(seqAId, seqBId)
                    .map(seq -> CompletableFuture.supplyAsync(() -> WEB_CLIENT.getSequence(webRequestProperties.getUrl(),
                            seq), executor))
                    .toArray(CompletableFuture[]::new);

            // Wait for all futures to complete
            CompletableFuture.allOf(seqFutures);

            // Shutdown exec service
            executor.shutdown();
            executor.close();

            // Resolve futures
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
                // Write success response
                os.write("File created successfully".getBytes());
            }
        }

}