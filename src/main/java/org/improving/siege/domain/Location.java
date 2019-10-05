package org.improving.siege.domain;

import java.util.ArrayList;
import java.util.List;

public class Location extends Container {
    private final String name;
    private final List<Exit> exits;
    private Enemy enemy;

    public Location(String name, Item... items) {
        super(items);
        this.name = name;
        this.exits = new ArrayList<>();
        this.enemy = null;
    }

    public String getName() {
        return name;
    }

    public List<Exit> getExits() {
        return exits;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        if (enemy != null) enemy.setLocation(this);
    }

    public boolean matchesNameOrAlias(String name) {
        return this.getName().equalsIgnoreCase(name);
    }
}
