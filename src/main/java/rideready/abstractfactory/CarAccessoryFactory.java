package rideready.abstractfactory;

public final class CarAccessoryFactory implements AccessoryFactory {
    @Override public SafetyEquipment createSafetyEquipment() { return new ChildSeat(); }
    @Override public SecurityDevice createSecurityDevice() { return new CarAlarm(); }
}
