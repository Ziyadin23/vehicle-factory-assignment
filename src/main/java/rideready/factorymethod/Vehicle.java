package rideready.factorymethod;

/** Product contract used by the creator's rental workflow. */
public interface Vehicle {
    String description();
    int wheels();
}
