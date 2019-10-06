package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.domain.Enemy;
import org.improving.siege.domain.Exit;
import org.improving.siege.domain.WorldItem;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class LookCommand extends AliasedCommand {
    public LookCommand(GameContext context, InputOutput io) {
        super(context, io);
    }

    @Override
    public String getHelpText() {
        return "Look around";
    }

    @Override
    public Stream<String> getAliases() {
        return Stream.of("look", "l");
    }

    @Override
    public void execute(String input)  throws GameException {
        var l = context.getPlayer().getLocation();
        io.displayText(l.getName());
        if (l.findAll(Enemy.class).count() > 0) {
            io.displayAlert("Enemies block your way.");
        }
        l.findAll(WorldItem.class)
                .forEach(e -> io.displayAlert(e.toString()));
        l.findAll(Exit.class)
                .forEach(e -> io.displayIndent(e.getName()));
    }
}
