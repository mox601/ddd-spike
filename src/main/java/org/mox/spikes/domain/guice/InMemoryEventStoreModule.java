package org.mox.spikes.domain.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.mox.spikes.domain.repository.events.EventStore;
import org.mox.spikes.domain.repository.events.InMemoryEventStore;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class InMemoryEventStoreModule extends AbstractModule {

    @Override
    protected void configure() {
        //TODO manage singleton construction explicitly in InMemoryStore
        bind(EventStore.class).to(InMemoryEventStore.class).in(Scopes.SINGLETON);
    }
}
