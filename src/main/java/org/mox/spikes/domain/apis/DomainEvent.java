package org.mox.spikes.domain.apis;

import org.joda.time.DateTime;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface DomainEvent {

    public int eventVersion();

    public DateTime occurredOn();

}
