package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.domain.Location;
import org.improving.siege.io.InputOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class LocationCommand extends AliasedCommand {
    private final List<Location> locations = new ArrayList<>();

    public LocationCommand(GameContext context, InputOutput io, Location... locations) {
        super(context, io);
        this.locations.addAll(Arrays.asList(locations));
    }

    @Override
    public boolean isValid(String input) {
        if (locations.contains(context.getPlayer().getLocation()))
            return super.isValid(input);
        return false;
    }
}
