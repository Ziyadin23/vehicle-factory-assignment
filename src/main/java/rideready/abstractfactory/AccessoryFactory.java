package rideready.abstractfactory;

/** Abstract factory: creates both product types in one compatible family. */
public interface AccessoryFactory {
    SafetyEquipment createSafetyEquipment();
    SecurityDevice createSecurityDevice();
}
