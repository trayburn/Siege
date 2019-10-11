package org.improving.siege;

import org.improving.siege.domain.EquippableWorldItem;
import org.improving.siege.domain.Location;
import org.improving.siege.domain.Player;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.exceptions.ItemNotFoundGameException;
import org.springframework.stereotype.Component;

@Component
public class GameContext {
    private Player player;
    private Location masterLocation;

    public GameContext(WorldFactory factory) {
        this.masterLocation = factory.buildMasterLocation();
        initPlayer();
    }

    public Location find(String name) {
        try {
            return masterLocation.find(Location.class, name);
        } catch (ItemNotFoundGameException e) {
            return null;
        }
    }

    private Player initPlayer() {
        this.player = new Player("The Player", 5,5,5);
        var sword = new EquippableWorldItem("Sword", "sword", 1,0,0);
        var shield = new EquippableWorldItem("Shield", "shield",0,0,1);
        player.getItems().add(sword);
        player.getItems().add(shield);
        try {
            player.equip(sword);
            var forest = this.masterLocation.findById(Location.class, "forest");
            this.player.setLocation(forest);
        } catch (GameException ex) {
            // This simply can't really happen.
            ex.printStackTrace();
        }
        return player;
    }

    public Player getPlayer() {
        return player;
    }
}
