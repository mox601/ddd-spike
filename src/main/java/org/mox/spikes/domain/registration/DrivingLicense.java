package org.mox.spikes.domain.registration;

/**
 * @author Matteo Moci ( matteo (dot) moci (at) gmail (dot) com )
 */
public class DrivingLicense {

    private int tireSize;

    private PlateId plateId;

    public DrivingLicense(int tireSize, PlateId plateId) {

        this.tireSize = tireSize;
        this.plateId = plateId;
    }

    public void updateTireSize(int tireSize) {

        this.tireSize = tireSize;
        //TODO publish event

    }
}
