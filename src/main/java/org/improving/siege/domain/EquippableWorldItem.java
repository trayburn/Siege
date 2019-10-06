package org.improving.siege.domain;

public class EquippableWorldItem extends WorldItem implements Equippable {

    public EquippableWorldItem(String name, String... aliases) {
        super(name, aliases);
    }

    public EquippableWorldItem(String name, int strength, int dexterity, int constitution, String... aliases) {
        super(name, strength, dexterity, constitution, aliases);
    }
}
