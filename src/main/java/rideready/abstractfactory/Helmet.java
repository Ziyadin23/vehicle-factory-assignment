package rideready.abstractfactory;

public final class Helmet implements SafetyEquipment {
    @Override public VehicleCategory category() { return VehicleCategory.MOTORCYCLE; }
    @Override public String description() { return "Motorcycle helmet"; }
}
