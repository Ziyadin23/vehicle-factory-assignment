package rideready.abstractfactory;

public final class DiscLock implements SecurityDevice {
    @Override public VehicleCategory category() { return VehicleCategory.MOTORCYCLE; }
    @Override public String description() { return "Motorcycle disc lock"; }
}
