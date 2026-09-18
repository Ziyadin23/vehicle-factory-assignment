package rideready.factorymethod;

public final class Car implements Vehicle {
    @Override public String description() { return "Car"; }
    @Override public int wheels() { return 4; }
}
