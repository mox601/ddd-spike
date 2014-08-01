# Things to do

* let guice injector create DomainEventPublisherResetter, a class used in interceptor
(see guice wiki) DONE
* add EventStore as dependency of DomainEventHandler for persistence DONE
* make examples for different event handlers: eventstore persister handler, DONE
* implement a full example of an event sourced aggregate:
studying  EventStoreCalendarRepository for an example of repository
of event sourced aggregates
 implement EventStoreInMemThermometerRepository - how to implement delete? - DOING
* implement InMemoryEventStore - DOING
* make a clear distinction between domain event eventstore and
event sourcing eventstore - DOING
* an EventStreamId has a streamVersion. On storing N Events, their version go from current
 streamVersion to streamVersion + N. EventStreamId is built using mutatedVersion of
 EventSourcedRootEntity
* lightweight handlers, etc...
* here domainevent is abstract instead of interface. which is better?
* implement 2pc on actions performed by most handlers (e.g. forwarder, etc... )
to offer the ability to rollback (see also UnitOfWork in iddd)
* what happens when aggregates are redesigned? e.g. changing data model,
changing types, do a test with past event replaying...
