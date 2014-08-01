package org.mox.spikes.domain.customisation;

import org.mox.spikes.domain.customisation.commands.ApplyCustomTires;
import org.mox.spikes.domain.handlers.CustomisationEventsHandler;
import org.mox.spikes.domain.registration.DrivingLicense;
import org.mox.spikes.domain.registration.PlateId;
import org.mox.spikes.domain.registration.RegistrationService;
import org.mox.spikes.domain.repository.InMemoryCarRepository;
import org.mox.spikes.domain.repository.InMemoryDrivingLicenceRepository;
import org.mox.spikes.domain.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.easymock.EasyMock.*;
import static org.testng.Assert.assertTrue;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class MultiThreadedCustomisationTestCase {


    private ExecutorService executorService;

    private CustomisationService customisationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(
            MultiThreadedCustomisationTestCase.class);

    private Repository<CarId, Car> mockCarRepository;

    private Repository<PlateId, DrivingLicense> mockDrivingLicenseRepository;

    private AtomicInteger counter;

    @BeforeClass
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {

        this.counter = new AtomicInteger(0);

        final HashMap<CarId, Car> cars = new HashMap<CarId, Car>();
        final CarId oneCarId = new CarId("1");
        cars.put(oneCarId, new Car(oneCarId));

        final HashMap<PlateId, DrivingLicense> drivingLicenses = new HashMap<PlateId, DrivingLicense>();
        final PlateId onePlateId = new PlateId("1");
        drivingLicenses.put(onePlateId, new DrivingLicense(1, onePlateId));

        this.mockCarRepository = new InMemoryCarRepository(cars);
        this.mockDrivingLicenseRepository = new InMemoryDrivingLicenceRepository(drivingLicenses);

        this.executorService = Executors.newFixedThreadPool(100);
        this.customisationService = new CustomisationService(this.mockCarRepository,
                                                             new CustomisationEventsHandler(
                                                                     new RegistrationService(
                                                                             this.mockDrivingLicenseRepository)));
    }

    @AfterClass
    public void tearDown() throws Exception {

        //TODO move to test
        this.executorService.shutdownNow();
        assertTrue(this.executorService.isShutdown());
        LOGGER.info("counter: " + this.counter.get());

    }

    @Test
    public void testName() throws Exception {

//        setupExpectations();
//        replay(this.mockCarRepository, this.mockDrivingLicenseRepository);

        final int clients = 100;
        for (int i = 0; i < clients; i++) {
            this.executorService.submit(new CustomTiresCommandClient());
        }

        Thread.sleep(1000L);

//        verify(this.mockCarRepository, this.mockDrivingLicenseRepository);

    }

    private void setupExpectations() throws Exception {

        final Car carToReturn = new Car(new CarId("1"));

        this.mockCarRepository.getById(anyObject(CarId.class));
        expectLastCall().andReturn(carToReturn).atLeastOnce();

        this.mockCarRepository.update(anyObject(CarId.class), anyObject(Car.class));
        expectLastCall().andReturn(carToReturn).atLeastOnce();

        final DrivingLicense drivingLicense = new DrivingLicense(11, new PlateId("1"));

//        this.mockDrivingLicenseRepository.getById(anyObject(PlateId.class));
//        expectLastCall().andReturn(drivingLicense).atLeastOnce();

//        this.mockDrivingLicenseRepository
//                .update(anyObject(PlateId.class), anyObject(DrivingLicense.class));
//        expectLastCall().andReturn(drivingLicense).atLeastOnce();
    }

    private class CustomTiresCommandClient implements Runnable {

        @Override
        public void run() {

            customisationService.handle(new ApplyCustomTires(new CarId("1"), 18));
//            LOGGER.info(this + " handled command");
            counter.incrementAndGet();
//            LOGGER.info("ran " + this);
        }
    }
}
