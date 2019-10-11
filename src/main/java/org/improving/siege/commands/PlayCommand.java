package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.domain.BlackJackSimulator;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class PlayCommand extends LocationCommand {
    private final BlackJackSimulator simulator;
    public PlayCommand(BlackJackSimulator simulator, GameContext context, InputOutput io) {
        super(context, io, context.find("The Fairy Casino"));
        this.simulator = simulator;
    }

    @Override
    public Stream<String> getAliases() {
        return Stream.of("play", "blackjack", "deal");
    }

    @Override
    public void execute(String input) throws GameException {
        if (simulator.isStarted() && simulator.isComplete() == false) {
            io.displayAlert("You are already playing a game");
        } else if (simulator.isStarted() && simulator.isComplete()) {
            io.displayAlert("You shuffled up a new game");
            simulator.newGame();
        } else {
            simulator.start();
            io.displayAlert("You begin to play Blackjack");
        }
        simulator.output(io);
    }


}
