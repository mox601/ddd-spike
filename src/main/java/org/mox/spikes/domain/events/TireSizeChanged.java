package org.mox.spikes.domain.events;

import org.joda.time.DateTime;
import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.registration.PlateId;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class TireSizeChanged implements DomainEvent {

    private final int eventVersion;

    private final DateTime occurredOn;

    private final PlateId plateId;

    private int size;

    private TireSizeChanged() {
        //jackson needs this
        this.eventVersion = -1;
        this.occurredOn = new DateTime(0);
        this.plateId = new PlateId("-1");
    }

    public TireSizeChanged(PlateId plateId, int size, DateTime happenedAt) {

        this.eventVersion = 1;
        this.occurredOn = happenedAt;
        this.plateId = plateId;
        this.size = size;
    }

    @Override
    public int eventVersion() {

        return eventVersion;
    }

    @Override
    public DateTime occurredOn() {

        return this.occurredOn;
    }

    public int getEventVersion() {

        return eventVersion;
    }

    public DateTime getOccurredOn() {

        return occurredOn;
    }

    public PlateId getPlateId() {

        return plateId;
    }

    public int getSize() {

        return size;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TireSizeChanged that = (TireSizeChanged) o;

        if (eventVersion != that.eventVersion) {
            return false;
        }
        if (size != that.size) {
            return false;
        }
        if (occurredOn != null ? !occurredOn.equals(that.occurredOn) : that.occurredOn != null) {
            return false;
        }
        if (plateId != null ? !plateId.equals(that.plateId) : that.plateId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = eventVersion;
        result = 31 * result + (occurredOn != null ? occurredOn.hashCode() : 0);
        result = 31 * result + (plateId != null ? plateId.hashCode() : 0);
        result = 31 * result + size;
        return result;
    }

    @Override
    public String toString() {

        return "TireSizeChanged{" +
                "eventVersion=" + eventVersion +
                ", occurredOn=" + occurredOn +
                ", plateId=" + plateId +
                ", size=" + size +
                '}';
    }
}
