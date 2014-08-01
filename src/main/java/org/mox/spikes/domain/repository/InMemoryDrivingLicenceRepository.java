package org.mox.spikes.domain.repository;

import com.google.inject.Inject;
import org.mox.spikes.domain.registration.DrivingLicense;
import org.mox.spikes.domain.registration.PlateId;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class InMemoryDrivingLicenceRepository
        extends BaseInMemoryRepository<PlateId, DrivingLicense> {

    @Inject
    public InMemoryDrivingLicenceRepository() {
        this(new HashMap<PlateId, DrivingLicense>());
    }

    public InMemoryDrivingLicenceRepository(
            Map<PlateId, DrivingLicense> snapshots) {

        super(snapshots);
    }

    @Override
    public PlateId nextIdentity() {

        return new PlateId(UUID.randomUUID().toString());
    }
}
