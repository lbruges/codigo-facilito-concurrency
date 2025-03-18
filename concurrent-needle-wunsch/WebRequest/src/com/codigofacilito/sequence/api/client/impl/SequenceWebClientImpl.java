package com.codigofacilito.sequence.api.client.impl;

import com.codigofacilito.common.props.model.req.WebRequestProperties;
import com.codigofacilito.sequence.api.client.SequenceWebClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SequenceWebClientImpl implements SequenceWebClient {

    private static final String SEQ_RESPONSE_PATTERN = "\"seq\":\"([^\"]+)\"";

    public String getSequence(WebRequestProperties requestProperties, String sequenceId) {
        try (HttpClient client = HttpClient.newHttpClient()) {
            String url = String.format(requestProperties.getUrl(), sequenceId);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Pattern pattern = Pattern.compile(SEQ_RESPONSE_PATTERN);
            String body = response.body();
            Matcher matcher = pattern.matcher(body);

            if (!matcher.find()) {
                throw new IllegalArgumentException("Not able to fetch sequence data");
            }

            return matcher.group(1);
        } catch (Exception e) {
            throw new RuntimeException("Unable to process request", e);
        }
    }

}
