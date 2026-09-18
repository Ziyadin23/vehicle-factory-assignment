package rideready.abstractfactory;

public final class ChildSeat implements SafetyEquipment {
    @Override public VehicleCategory category() { return VehicleCategory.CAR; }
    @Override public String description() { return "Car child seat"; }
}
