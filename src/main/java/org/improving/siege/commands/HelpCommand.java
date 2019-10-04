package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.io.InputOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class HelpCommand extends AliasedCommand {
    @Autowired
    private List<HelpEntry> commands;

    public HelpCommand(GameContext context, InputOutput io) {
        super(context, io);
    }

    @Override
    public Stream<String> getAliases() {
        return Stream.of("help", "h");
    }

    @Override
    public void execute(String input)  throws GameException {
        io.displayText("Help");
        for(var cmd : commands) {
            io.displayText("  " + cmd.getName() + " - " + cmd.getHelpText());
        }
    }
}
