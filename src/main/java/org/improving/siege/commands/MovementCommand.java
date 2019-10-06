package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.domain.Enemy;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class MovementCommand extends AliasedCommand{
    public MovementCommand(GameContext context, InputOutput io) {
        super(context,io);
    }

    @Override
    public Stream<String> getAliases() {
        var nameStream = context.getPlayer().getLocation().getExits()
                .map(e -> e.getName());
        var aliasStream = context.getPlayer().getLocation().getExits()
                .map(e -> e.getAliases()).flatMap(List::stream);
        return Stream.of(nameStream, aliasStream).flatMap(e -> e);
    }

    @Override
    public String getName() {
        return "<direction>";
    }

    @Override
    public String getHelpText() {
        return "Move in the direction indicated.";
    }

    @Override
    public void execute(String input) throws GameException {
        var location = context.getPlayer().getLocation();
        if (location.findAll(Enemy.class).count() > 0) {
            io.displayText("You must first defeat all enemies.");
            return;
        }
        var destination = location.getExits()
                .filter(e -> e.matchesNameOrAlias(input))
                .findFirst()
                .orElseThrow()
                .getDestination();
        context.getPlayer().setLocation(destination);
    }
}
