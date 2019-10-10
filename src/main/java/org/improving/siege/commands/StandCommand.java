package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.domain.BlackJackSimulator;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class StandCommand extends AliasedCommand {
    private final BlackJackSimulator simulator;
    public StandCommand(GameContext context, InputOutput io, BlackJackSimulator simulator) {
        super(context, io);
        this.simulator = simulator;
    }

    @Override
    public Stream<String> getAliases() {
        return Stream.of("stand");
    }

    @Override
    public boolean isValid(String input) {
        if (simulator.isStarted() == false) return false;
        return super.isValid(input);
    }

    @Override
    public void execute(String input) throws GameException {
        simulator.stand();
        simulator.output(io);
    }
}
