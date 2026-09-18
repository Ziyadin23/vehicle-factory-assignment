package rideready.factorymethod;

public final class Motorcycle implements Vehicle {
    @Override public String description() { return "Motorcycle"; }
    @Override public int wheels() { return 2; }
}
