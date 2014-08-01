package org.mox.spikes.domain.registration;

import com.google.inject.Inject;
import org.mox.spikes.domain.repository.Repository;
import org.mox.spikes.domain.repository.exceptions.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class RegistrationService {

    private final Repository<PlateId, DrivingLicense> drivingLicenseRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);

    @Inject
    public RegistrationService(final Repository<PlateId, DrivingLicense> drivingLicenseRepository) {

        this.drivingLicenseRepository = drivingLicenseRepository;
    }

    public void changeTireSizeOnDrivingLicence(final PlateId plateId, final int size) {

        final DrivingLicense licenceById;

        try {

            licenceById = this.drivingLicenseRepository.getById(plateId);

            if (licenceById != null) {

                licenceById.updateTireSize(size);

                this.drivingLicenseRepository.update(plateId, licenceById);

            }

        } catch (RepositoryException e) {
            e.printStackTrace();
        }


    }
}
