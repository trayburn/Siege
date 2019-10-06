package org.improving.siege.domain;

import java.util.stream.Stream;

public class Location extends Container {

    public Location(String name, String... aliases) {
        super(name, aliases);
    }

    public Location(String name, int strength, int dexterity, int constitution, String... aliases) {
        super(name, strength, dexterity, constitution, aliases);
    }

    public Stream<Exit> getExits() {
        return findAll(Exit.class);
    }

    public Enemy getEnemy() {
        return findAll(Enemy.class).findFirst().orElse(null);
    }
}
