package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.io.InputOutput;

import java.util.stream.Stream;

public abstract class AliasedCommand implements Command, HelpEntry {
    protected final GameContext context;
    protected final InputOutput io;
    public abstract Stream<String> getAliases();

    public AliasedCommand(GameContext context, InputOutput io) {
        this.context = context;
        this.io = io;
    }

    @Override
    public String getName() {
        return getAliases().findFirst().orElseThrow();
    }

    @Override
    public String getHelpText() {
        return "";
    }

    @Override
    public boolean isValid(String input) {
        var commandPart = getCommandPart(input);
        return getAliases().anyMatch(commandPart::equalsIgnoreCase);
    }

    protected String getCommandPart(String input) {
        return input.trim();
    }
    protected String[] getParameters(String input) {
        return new String[] {};
    }
}
