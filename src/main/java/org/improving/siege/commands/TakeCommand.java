package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.exceptions.ItemNotFoundGameException;
import org.improving.siege.exceptions.NoTargetGameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class TakeCommand extends AliasedCommand {
    public TakeCommand(GameContext context, InputOutput io) {
        super(context, io);
    }

    @Override
    public Stream<String> getAliases() {
        return Stream.of("take", "t", "get", "pickup");
    }

    @Override
    public String getHelpText() {
        return "Picks up an item from your location.";
    }

    @Override
    protected String getCommandPart(String input) {
        var splitArr = input.split(" ");
        return splitArr[0];
    }

    @Override
    protected String[] getParameters(String input) {
        var splitArr = input.split(" ");
        return Arrays.copyOfRange(splitArr, 1, splitArr.length);
    }

    @Override
    public void execute(String input) throws GameException {
        try {
            var params = getParameters(input);
            if (params.length == 0) throw new NoTargetGameException();
            var item = context.getPlayer().getLocation().find(params[0]);
            context.getPlayer().getLocation().getItems().remove(item);
            context.getPlayer().getItems().add(item);
            io.displayAlert("You pick up " + item.toString() + ".");
        } catch (ItemNotFoundGameException ex) {
            io.displayAlert("You don't see that here.");
        }
    }
}
