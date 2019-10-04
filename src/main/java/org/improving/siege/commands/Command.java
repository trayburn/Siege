package org.improving.siege.commands;

import org.improving.siege.exceptions.GameException;

public interface Command {
    boolean isValid(String input);
    void execute(String input) throws GameException;
}
