package org.improving.siege.domain;

public class EquippableItem extends Item implements Equippable {
    public EquippableItem(String name, String... aliases) {
        super(name, aliases);
    }
}
