package org.mox.spikes.domain.registration;

import org.mox.spikes.domain.apis.Entity;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class Plate implements Entity<Plate> {

    private PlateId id;

    private String description;

    public Plate(PlateId id, String description) {

        this.id = id;
        this.description = description;
    }

    public PlateId getId() {

        return id;
    }

    public String getDescription() {

        return description;
    }

    @Override
    public boolean sameIdentityAs(Plate other) {

        return this.id.sameValueAs(other.getId());
    }

}
