package org.mox.spikes.domain.repository;

import org.joda.time.DateTime;
import org.mox.spikes.domain.apis.DomainEventHandler;
import org.mox.spikes.domain.customisation.Car;
import org.mox.spikes.domain.customisation.CarId;
import org.mox.spikes.domain.registration.Plate;
import org.mox.spikes.domain.registration.PlateId;
import org.mox.spikes.domain.events.ColorChanged;
import org.mox.spikes.domain.apis.DomainEvent;
import org.mox.spikes.domain.events.TireSizeChanged;
import org.mox.spikes.domain.utils.Folder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class InMemoryCarRepositoryTestCase {

    private Repository<CarId, Car> carRepository;

    private Repository<PlateId, Plate> plateRepository;

    @BeforeClass
    public void setUp() throws Exception {
        this.carRepository = new InMemoryCarRepository(new HashMap<CarId, Car>());
    }

    @Test
    public void shouldStore() throws Exception {

        final String id = "id-123";
        final CarId carId = new CarId(id);
        //stores a car with default values
        this.carRepository.store(carId, new Car(carId));

        final Car car = this.carRepository.getById(carId);
        assertNotNull(car);
        assertEquals(car.getColor(), "");
        assertEquals(car.getTireSize(), 0);
    }

    @Test (enabled = false)
    public void testServiceUseCase() throws Exception {

        //only one aggregate is modified for each service method

        //create Plate
        PlateId id = null;
        Plate plate = new Plate(id, "");
        this.plateRepository.store(id, plate);

        //create Car
        CarId carId = null;
        Car car = new Car(carId);
        this.carRepository.store(carId, car);

        //assign Plate to Car

        //load plate by id
        final Plate plateToAssign = this.plateRepository.getById(id);

        Car toSell = this.carRepository.getById(new CarId("car-1"));
        toSell.assign(plateToAssign);
        this.carRepository.update(new CarId("car-1"), toSell);
        //TODO asserts
    }

    @Test
    public void testFoldingOnCarChanges() throws Exception {

        final CarId carId = new CarId("abc");
        final Car initialCar = new Car(carId);
        //just because the handler is the entity itself
        final DomainEventHandler<DomainEvent> handler = initialCar;

        final Car.CarClosure closure = new Car.CarClosure(initialCar, handler);

        //these should be ordered by date
        final List<DomainEvent> changes = Arrays.asList(
                new TireSizeChanged(new PlateId(carId.getId()), 100, new DateTime(0L)),
                new ColorChanged(carId, "blue", new DateTime(1L)),
                new TireSizeChanged(new PlateId(carId.getId()), 130, new DateTime(2L)));

        final Car changedCar = Folder.foreach(changes, closure);

        assertEquals(changedCar.getColor(), "blue");
        assertEquals(changedCar.getTireSize(), 130);
    }

    @Test
    public void testChangesInDomainModelShouldFireEventsRecordedInEventStore() throws Exception {

        //build an object
        //store it
        //register in-memory-listener of changes in some way
        //change it using business methods
        //retrieve events catched by listener
        //check they are ok

        //then, load it from the event-store backed persistence
        //load the snapshot and apply it the events happened since
    }
}
