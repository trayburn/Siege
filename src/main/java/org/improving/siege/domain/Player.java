package org.improving.siege.domain;

import org.improving.siege.exceptions.NotCarriedGameException;

public class Player extends Character {
    public Player(String name, int strength, int dexterity, int constitution) {
        super(name, strength, dexterity, constitution);
        var sword = new EquippableWorldItem("Sword",1,0,0);
        var shield = new EquippableWorldItem("Shield",0,0,1);
        this.items.add(sword);
        this.items.add(shield);
        try {
            this.equip(sword);
        } catch (NotCarriedGameException ex) {
            // This simply can't really happen.
        }
    }
}
