package org.improving.siege.domain;

public class Exit extends Item {
    private Location destination;

    public Exit(String name, Location destination, String... aliases) {
        super(name, aliases);
        this.destination = destination;
    }

    public Location getDestination() {
        return destination;
    }
}
