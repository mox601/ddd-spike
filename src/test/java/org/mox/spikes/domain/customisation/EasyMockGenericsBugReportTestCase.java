package org.mox.spikes.domain.customisation;

import org.mox.spikes.domain.registration.DrivingLicense;
import org.mox.spikes.domain.registration.PlateId;
import org.mox.spikes.domain.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.*;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class EasyMockGenericsBugReportTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MultiThreadedCustomisationTestCase.class);

    private Repository<CarId, Car> mockCarRepository;

    private Repository<PlateId, DrivingLicense> mockDrivingLicenseRepository;

    private final CarId id = new CarId("1");

    private final PlateId plateId = new PlateId("1");


    @BeforeClass
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {

        //verified: easymock plays well with two generics of the same class
        //TODO test is green, try to tweak it and make it red in some way?
        //mock repositories
        this.mockCarRepository = createStrictMock(Repository.class);
        this.mockDrivingLicenseRepository = createStrictMock(Repository.class);
    }

    @Test
    public void testName() throws Exception {

        setupExpectations();

        replay(this.mockCarRepository);
        replay(this.mockDrivingLicenseRepository);

        //car
        final Car carById = mockCarRepository.getById(id);
        final Car updatedCar = mockCarRepository.update(id, new Car(id));

        //driving license
        final DrivingLicense licenseById = this.mockDrivingLicenseRepository.getById(plateId);
        final DrivingLicense updatedLicense = this.mockDrivingLicenseRepository
                .update(plateId, new DrivingLicense(1, plateId));

        verify(this.mockCarRepository);
        verify(this.mockDrivingLicenseRepository);

    }

    //TODO
    private void setupExpectations() throws Exception {

        final Car carToReturn = new Car(new CarId("1"));

        this.mockCarRepository.getById(anyObject(CarId.class));
        expectLastCall().andReturn(carToReturn).atLeastOnce();

        this.mockCarRepository.update(anyObject(CarId.class), anyObject(Car.class));
        expectLastCall().andReturn(carToReturn).atLeastOnce();

        final DrivingLicense drivingLicense = new DrivingLicense(11, new PlateId("1"));

        this.mockDrivingLicenseRepository.getById(anyObject(PlateId.class));
        expectLastCall().andReturn(drivingLicense).atLeastOnce();

        this.mockDrivingLicenseRepository
                .update(anyObject(PlateId.class), anyObject(DrivingLicense.class));
        expectLastCall().andReturn(drivingLicense).atLeastOnce();
    }

}
