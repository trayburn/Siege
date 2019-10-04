package org.improving.siege.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exit {
    private String name;
    private Location destination;
    private List<String> aliases;

    public Exit(String name, Location destination, String... aliases) {
        this.name = name;
        this.destination = destination;
        this.aliases = new ArrayList<>();
        this.aliases.addAll(Arrays.asList(aliases));
    }

    public String getName() {
        return name;
    }

    public Location getDestination() {
        return destination;
    }

    public List<String> getAliases() {
        return aliases;
    }
}
