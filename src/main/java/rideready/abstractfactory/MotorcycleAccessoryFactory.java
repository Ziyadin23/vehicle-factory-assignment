package rideready.abstractfactory;

public final class MotorcycleAccessoryFactory implements AccessoryFactory {
    @Override public SafetyEquipment createSafetyEquipment() { return new Helmet(); }
    @Override public SecurityDevice createSecurityDevice() { return new DiscLock(); }
}
