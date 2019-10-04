package org.improving.siege;

import org.improving.siege.commands.Command;
import org.improving.siege.exceptions.ExitGameException;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Game {
    private final InputOutput io;
    private final GameContext context;
    private final List<Command> commands;

    public Game(List<Command> commands, InputOutput io, GameContext context) {
        this.io = io;
        this.context = context;
        this.commands = commands;
    }

    public void run() {
        boolean loop = true;
        while (loop) {
            try {
                io.displayPrompt(context.getPlayer().getLocation().getName() + "> ");
                var input = io.getInput();
                var cmd = commands.stream().filter(c -> c.isValid(input)).findFirst();
                if (cmd.isPresent()) {
                    cmd.get().execute(input);
                } else {
                    io.displayText("You can't do that here");
                }
            } catch (ExitGameException ex) {
                loop = false;
            } catch (GameException ex) {
                io.displayAlert("Exception Occurred:");
                io.displayAlert(ex.toString());
                loop = false;
            }
        }

    }
}
