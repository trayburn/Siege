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
public class DropCommand extends AliasedCommand {
    public DropCommand(GameContext context, InputOutput io) {
        super(context, io);
    }

    @Override
    public Stream<String> getAliases() {
        return Stream.of("drop", "d");
    }

    @Override
    public String getHelpText() {
        return "Drops an item to your location.";
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
            var item = context.getPlayer().find(params[0]);
            context.getPlayer().getItems().remove(item);
            context.getPlayer().getLocation().getItems().add(item);
            io.displayAlert("You drop " + item.toString() + ".");
        } catch (ItemNotFoundGameException ex) {
            io.displayAlert("You aren't carrying that right now.");
        }
    }
}
