package rideready;

import rideready.abstractfactory.AccessoryFactory;
import rideready.abstractfactory.CarAccessoryFactory;
import rideready.abstractfactory.MotorcycleAccessoryFactory;
import rideready.client.AccessoryRental;
import rideready.factorymethod.CarFactory;
import rideready.factorymethod.MotorcycleFactory;
import rideready.factorymethod.VehicleFactory;

/** Composition root: the only application location that chooses concrete factories. */
public final class Main {
    private Main() { }

    public static void main(String[] args) {
        System.out.println("RideReady | Factory Method & Abstract Factory");
        demonstrate("Motorcycle rental", new MotorcycleFactory(), new MotorcycleAccessoryFactory());
        demonstrate("Car rental", new CarFactory(), new CarAccessoryFactory());
    }

    private static void demonstrate(String title, VehicleFactory vehicles, AccessoryFactory accessories) {
        System.out.println("\n" + title);
        System.out.println("  Part A - " + vehicles.prepareRental());
        System.out.println("  Part B - " + new AccessoryRental(accessories).summary());
    }
}
