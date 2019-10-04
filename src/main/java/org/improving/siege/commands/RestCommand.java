package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class RestCommand extends AliasedCommand {
    public RestCommand(GameContext context, InputOutput io) {
        super(context, io);
    }

    @Override
    public String getHelpText() {
        return "Rest and recover some hit points.";
    }

    @Override
    public Stream<String> getAliases() {
        return Stream.of("rest");
    }

    @Override
    public void execute(String input) throws GameException {
        var p = context.getPlayer();

        if (p.getLocation().getEnemy() != null) {
            io.displayAlert("You cannot rest here.");
            return;
        }

        p.setHitPoints((p.getHitPoints() + p.getConstitution()) > p.getMaxHitPoints() ? p.getMaxHitPoints() : (p.getHitPoints() + p.getConstitution()));
        io.displayAlert("You rest and recover");
    }
}
