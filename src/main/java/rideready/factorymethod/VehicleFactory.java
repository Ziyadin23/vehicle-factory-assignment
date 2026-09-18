package rideready.factorymethod;

/** Creator: shares the workflow but delegates the product choice to subclasses. */
public abstract class VehicleFactory {
    public abstract Vehicle createVehicle();

    public final String prepareRental() {
        Vehicle vehicle = createVehicle();
        return "Ready: " + vehicle.description() + " (" + vehicle.wheels() + " wheels)";
    }
}
