package org.mox.spikes.domain.apis;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface DomainEventHandler<E extends DomainEvent> {

    void apply(final E change);

}
