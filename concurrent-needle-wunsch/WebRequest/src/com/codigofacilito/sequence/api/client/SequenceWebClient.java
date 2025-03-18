package com.codigofacilito.sequence.api.client;

/**
 * Simple web client.
 */
@FunctionalInterface
public interface SequenceWebClient {

    /**
     * Get a DNA sequence based on the sequence id.
     * @param baseUrl base url
     * @param sequenceId sequence id
     * @return sequence data (if found)
     */
    String getSequence(String baseUrl, String sequenceId);

}
