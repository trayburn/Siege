package org.improving.siege;

import org.improving.siege.domain.Location;
import org.improving.siege.domain.Player;
import org.springframework.stereotype.Component;

@Component
public class GameContext {
    private final Player player;
    private final Location startingLocation;

    public GameContext(WorldFactory factory) {
        this.player = new Player("The Player", 5,5,5);
        this.startingLocation = factory.getStartingLocation();
        this.player.setLocation(this.startingLocation);
    }

    public Player getPlayer() {
        return player;
    }

    public Location getStartingLocation() {
        return startingLocation;
    }
}
