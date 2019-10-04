package org.improving.siege;

import org.improving.siege.domain.*;
import org.springframework.stereotype.Component;

@Component
public class WorldFactory {
    private Location startingLocation = null;

    public Location getStartingLocation() {
        if (startingLocation != null) return startingLocation;

        var forest = new Location("Forest");
        var clearing = new Location("The Clearing");
        var pond = new Location("The Pond");
        var road = new Location("The King's Road");

        forest.getItems().add(new StatisticItem(
                "A golden ring",
                0, 1, 0,
                "golden", "ring"));

        var lady = new Enemy("The Lady of the Lake", 2, 5, 10);
        lady.getItems().add(new Item("Excalibur"));
        pond.setEnemy(lady);

        forest.getExits().add(new Exit("The path north", clearing, "n", "north"));
        clearing.getExits().add(new Exit("The path south", forest, "s", "south"));

        forest.getExits().add(new Exit("The path south", pond, "s", "south"));
        pond.getExits().add(new Exit("The path north", forest, "n", "north"));

        forest.getExits().add(new Exit("The path east", road, "e", "east"));
        road.getExits().add(new Exit("The path west", forest, "w", "west"));

        return forest;
    }
}
