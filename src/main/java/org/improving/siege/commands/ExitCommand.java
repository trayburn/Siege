package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.exceptions.ExitGameException;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class ExitCommand extends AliasedCommand {
    public ExitCommand(GameContext context, InputOutput io) {
        super(context, io);
    }

    @Override
    public Stream<String> getAliases() {
        return Stream.of("exit", "quit");
    }

    @Override
    public void execute(String input) throws GameException {
        io.displayAlert("Goodbye");
        throw new ExitGameException();
    }

    @Override
    public String getHelpText() {
        return "Quits the game.";
    }
}
