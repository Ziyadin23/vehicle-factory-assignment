package rideready;

import rideready.abstractfactory.*;
import rideready.client.AccessoryRental;
import rideready.factorymethod.*;

/** Behavioral tests without external libraries. Failures exit the JVM nonzero. */
public final class PatternTests {
    private static int passed;
    private PatternTests() { }

    public static void main(String[] args) {
        check("motorcycle contract", new MotorcycleFactory().createVehicle().wheels() == 2);
        check("car contract", new CarFactory().createVehicle().wheels() == 4);
        check("motorcycle workflow", new MotorcycleFactory().prepareRental().equals("Ready: Motorcycle (2 wheels)"));
        check("car workflow", new CarFactory().prepareRental().equals("Ready: Car (4 wheels)"));
        checkFamily(new MotorcycleAccessoryFactory(), VehicleCategory.MOTORCYCLE);
        checkFamily(new CarAccessoryFactory(), VehicleCategory.CAR);
        check("motorcycle client", new AccessoryRental(new MotorcycleAccessoryFactory()).summary()
                .equals("Motorcycle helmet + Motorcycle disc lock"));
        check("car client", new AccessoryRental(new CarAccessoryFactory()).summary()
                .equals("Car child seat + Car alarm"));
        expect("null factory", NullPointerException.class, () -> new AccessoryRental(null));
        expect("null safety", NullPointerException.class, () -> new AccessoryRental(factory(null, new CarAlarm())));
        expect("null security", NullPointerException.class, () -> new AccessoryRental(factory(new ChildSeat(), null)));
        expect("mixed families", IllegalArgumentException.class,
                () -> new AccessoryRental(factory(new Helmet(), new CarAlarm())));

        // A new creator works with the inherited workflow, without changing VehicleFactory.
        VehicleFactory bicycleFactory = new VehicleFactory() {
            @Override public Vehicle createVehicle() {
                return new Vehicle() {
                    @Override public String description() { return "Bicycle"; }
                    @Override public int wheels() { return 2; }
                };
            }
        };
        check("creator extension", bicycleFactory.prepareRental().equals("Ready: Bicycle (2 wheels)"));

        // A custom family implementation can be injected without changing the client.
        SafetyEquipment customSafety = new SafetyEquipment() {
            @Override public VehicleCategory category() { return VehicleCategory.CAR; }
            @Override public String description() { return "Booster seat"; }
        };
        check("interface substitution", new AccessoryRental(factory(customSafety, new CarAlarm()))
                .summary().equals("Booster seat + Car alarm"));
        System.out.println("PASS: " + passed + " behavioral checks");
    }

    private static AccessoryFactory factory(SafetyEquipment safety, SecurityDevice security) {
        return new AccessoryFactory() {
            @Override public SafetyEquipment createSafetyEquipment() { return safety; }
            @Override public SecurityDevice createSecurityDevice() { return security; }
        };
    }

    private static void checkFamily(AccessoryFactory factory, VehicleCategory expected) {
        check(expected + " safety family", factory.createSafetyEquipment().category() == expected);
        check(expected + " security family", factory.createSecurityDevice().category() == expected);
    }

    private static void check(String name, boolean condition) {
        if (!condition) { throw new AssertionError(name); }
        passed++;
        System.out.println("PASS " + name);
    }

    private static void expect(String name, Class<? extends RuntimeException> type, Runnable action) {
        try {
            action.run();
        } catch (RuntimeException error) {
            check(name, type.isInstance(error));
            return;
        }
        throw new AssertionError(name + ": expected " + type.getSimpleName());
    }
}
