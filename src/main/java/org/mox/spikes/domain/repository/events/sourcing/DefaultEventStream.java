package org.mox.spikes.domain.repository.events.sourcing;

import org.mox.spikes.domain.apis.DomainEvent;

import java.util.List;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class DefaultEventStream implements EventStream {

    private List<DomainEvent> events;

    private int version;

    public DefaultEventStream(List<DomainEvent> anEventsList, int aVersion) {

        super();

        this.setEvents(anEventsList);
        this.setVersion(aVersion);
    }

    @Override
    public List<DomainEvent> events() {

        return this.events;
    }

    @Override
    public int version() {

        return this.version;
    }

    private void setEvents(List<DomainEvent> anEventsList) {

        this.events = anEventsList;
    }

    private void setVersion(int aVersion) {

        this.version = aVersion;
    }

}
