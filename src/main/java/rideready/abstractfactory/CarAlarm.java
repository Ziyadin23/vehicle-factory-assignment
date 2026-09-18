package rideready.abstractfactory;

public final class CarAlarm implements SecurityDevice {
    @Override public VehicleCategory category() { return VehicleCategory.CAR; }
    @Override public String description() { return "Car alarm"; }
}
