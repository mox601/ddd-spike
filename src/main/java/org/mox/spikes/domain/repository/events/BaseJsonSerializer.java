package org.mox.spikes.domain.repository.events;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.util.TimeZone;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class BaseJsonSerializer {

    private final ObjectMapper mapper;

    protected BaseJsonSerializer() {

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        objectMapper.setDateFormat(new ISO8601DateFormat());
        objectMapper.setTimeZone(TimeZone.getDefault());
        objectMapper.setVisibilityChecker(VisibilityChecker.Std
                                                           .defaultInstance()
                                                           .withFieldVisibility(
                                                                   JsonAutoDetect.Visibility.ANY));
        this.mapper = objectMapper;
    }

    protected ObjectMapper getMapper() {

        return mapper;
    }
}
