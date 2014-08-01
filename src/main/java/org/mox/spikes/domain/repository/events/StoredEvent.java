package org.mox.spikes.domain.repository.events;

import org.joda.time.DateTime;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class StoredEvent {

    private final long eventId;

    private final String name;

    private final DateTime occurredOn;

    private final String eventBody;

    public StoredEvent(final long eventId, final String name, final DateTime occurredOn,
                       final String eventSerialization) {

        this.eventId = eventId;
        this.name = name;
        this.occurredOn = occurredOn;
        this.eventBody = eventSerialization;
    }

    public long getEventId() {

        return eventId;
    }

    public String getName() {

        return name;
    }

    public DateTime getOccurredOn() {

        return occurredOn;
    }

    public String getEventBody() {

        return eventBody;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StoredEvent that = (StoredEvent) o;

        if (eventId != that.eventId) {
            return false;
        }
        if (eventBody != null ? !eventBody.equals(that.eventBody) : that.eventBody != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (occurredOn != null ? !occurredOn.equals(that.occurredOn) : that.occurredOn != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = (int) (eventId ^ (eventId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (occurredOn != null ? occurredOn.hashCode() : 0);
        result = 31 * result + (eventBody != null ? eventBody.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        return "StoredEvent{" +
                "eventId=" + eventId +
                ", name='" + name + '\'' +
                ", occurredOn=" + occurredOn +
                ", eventBody='" + eventBody + '\'' +
                '}';
    }
}
