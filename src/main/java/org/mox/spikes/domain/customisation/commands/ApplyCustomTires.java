package org.mox.spikes.domain.customisation.commands;

import org.mox.spikes.domain.apis.Command;
import org.mox.spikes.domain.customisation.CarId;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class ApplyCustomTires implements Command {

    private CarId carId;

    private int tireSize;

    public ApplyCustomTires(final CarId carId, final int tireSize) {

        this.carId = carId;
        this.tireSize = tireSize;
    }

    public CarId getCarId() {

        return carId;
    }

    public int getTireSize() {

        return tireSize;
    }
}
