package org.mox.spikes.domain.infrastructure;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public interface DomainEventSubscriber<T> {

    /* whould it be better to have a method like:
    * boolean subscribedTo(DomainEvent event)
    *
    * PRO:
    * one handler can handle different event classes, implementing different handle(event) methods
    *
    * CONTRO:
    * logic to test if handler is subscribed should be replicated in every handler,
    * instead of having in publisher
    *
    * */
    Class<T> subscribedToEventType();

    void handleEvent(T aDomainEvent);
}
