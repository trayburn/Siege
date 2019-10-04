package org.improving.siege.commands;

import org.improving.siege.GameContext;
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
        if (l.getEnemy() != null) {
            io.displayAlert(l.getEnemy().getName() + " blocks your way.");
        }
        for (var item : l.getItems()) {
            io.displayAlert(item.toString());
        }
        for (var e : l.getExits()) {

            io.displayIndent(e.getName());
        }
    }
}
