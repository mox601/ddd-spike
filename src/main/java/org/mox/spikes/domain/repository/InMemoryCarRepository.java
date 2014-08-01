package org.mox.spikes.domain.repository;

import com.google.inject.Inject;
import org.mox.spikes.domain.customisation.Car;
import org.mox.spikes.domain.customisation.CarId;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class InMemoryCarRepository extends BaseInMemoryRepository<CarId, Car> {

    @Inject
    public InMemoryCarRepository() {
           this(new HashMap<CarId, Car>());
    }

    public InMemoryCarRepository(final Map<CarId, Car> snapshots) {
        super(snapshots);
    }

    @Override
    public CarId nextIdentity() {

        return new CarId(UUID.randomUUID().toString());
    }
}
