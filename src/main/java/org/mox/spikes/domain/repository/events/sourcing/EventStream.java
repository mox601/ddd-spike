package org.mox.spikes.domain.repository.events.sourcing;

import org.mox.spikes.domain.apis.DomainEvent;

import java.util.List;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface EventStream {

    public List<DomainEvent> events();

    public int version();

}
