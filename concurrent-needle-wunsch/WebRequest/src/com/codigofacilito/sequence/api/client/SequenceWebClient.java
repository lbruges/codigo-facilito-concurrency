package com.codigofacilito.sequence.api.client;

import com.codigofacilito.common.props.model.req.WebRequestProperties;

@FunctionalInterface
public interface SequenceWebClient {

    String getSequence(WebRequestProperties requestProperties, String sequenceId);

}
