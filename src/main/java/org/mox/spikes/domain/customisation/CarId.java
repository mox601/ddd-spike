package org.mox.spikes.domain.customisation;

import org.mox.spikes.domain.apis.ValueObject;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class CarId implements ValueObject<CarId> {

    private String id;

    public CarId(String id) {

        this.id = id;
    }

    @Override
    public boolean sameValueAs(CarId other) {

        return this.equals(other);
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

        CarId carId = (CarId) o;

        if (id != null ? !id.equals(carId.id) : carId.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        return id != null ? id.hashCode() : 0;
    }
}
