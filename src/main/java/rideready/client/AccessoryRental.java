package rideready.client;

import java.util.Objects;
import rideready.abstractfactory.AccessoryFactory;
import rideready.abstractfactory.SafetyEquipment;
import rideready.abstractfactory.SecurityDevice;

/** Client depends only on factory/product interfaces, never concrete products. */
public final class AccessoryRental {
    private final SafetyEquipment safety;
    private final SecurityDevice security;

    public AccessoryRental(AccessoryFactory factory) {
        Objects.requireNonNull(factory, "Accessory factory is required");
        safety = Objects.requireNonNull(factory.createSafetyEquipment(), "Safety equipment is required");
        security = Objects.requireNonNull(factory.createSecurityDevice(), "Security device is required");
        if (safety.category() != security.category()) {
            throw new IllegalArgumentException("Accessories must belong to the same vehicle family");
        }
    }

    public String summary() {
        return safety.description() + " + " + security.description();
    }
}
