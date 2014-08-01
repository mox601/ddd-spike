package org.mox.spikes.domain.registration;

import org.mox.spikes.domain.apis.ValueObject;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class PlateId implements ValueObject<PlateId> {

    private String id;

    private PlateId() {}

    public PlateId(String id) {

        this.id = id;
    }

    public String getId() {

        return id;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlateId plateId = (PlateId) o;

        if (id != null ? !id.equals(plateId.id) : plateId.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean sameValueAs(PlateId other) {

        return this.equals(other);
    }

    @Override
    public String toString() {

        return "PlateId{" +
                "id='" + id + '\'' +
                '}';
    }
}
