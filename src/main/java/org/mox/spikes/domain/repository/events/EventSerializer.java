package org.mox.spikes.domain.repository.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.mox.spikes.domain.apis.DomainEvent;

import java.io.IOException;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class EventSerializer extends BaseJsonSerializer {

    private static EventSerializer instance;

    private EventSerializer() {

        super();
    }

    public static synchronized EventSerializer instance() {

        if (instance == null) {
            instance = new EventSerializer();
        }

        return instance;
    }

    public String serialize(final DomainEvent aDomainEvent) throws JsonProcessingException {

        return this.getMapper().writeValueAsString(aDomainEvent);
    }

    public <T extends DomainEvent> T deserialize(final String aSerialization,
                                                 final Class<T> aType) throws IOException {

        return this.getMapper().readValue(aSerialization, aType);
    }

}
