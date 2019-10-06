package org.improving.siege.domain;

public class WorldItem extends Item {
    public WorldItem(String name, String... aliases) {
        super(name, aliases);
    }

    public WorldItem(String name, int strength, int dexterity, int constitution, String... aliases) {
        super(name, strength, dexterity, constitution, aliases);
    }
}
