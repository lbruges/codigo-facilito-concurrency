package com.codigofacilito.sequence.api.client.impl;

import com.codigofacilito.sequence.api.client.SequenceWebClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SequenceWebClientImpl implements SequenceWebClient {

    private static final String SEQ_RESPONSE_PATTERN = "\"seq\":\"([^\"]+)\"";

    public String getSequence(String baseUrl, String sequenceId) {
        // Declare web client
        try (HttpClient client = HttpClient.newHttpClient()) {
            // This assumes that the specified url contains a %s token for formatting
            String url = String.format(baseUrl, sequenceId);

            // Create get request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Extract the response based on the pattern to extract from the JSON response
            Pattern pattern = Pattern.compile(SEQ_RESPONSE_PATTERN);
            String body = response.body();
            Matcher matcher = pattern.matcher(body);

            if (!matcher.find()) {
                // Interrupt if not found
                throw new IllegalArgumentException("Not able to fetch sequence data");
            }

            // Extract based on pattern matching
            return matcher.group(1);
        } catch (Exception e) {
            throw new RuntimeException("Unable to process request", e);
        }
    }

}
