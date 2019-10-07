package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.domain.Equippable;
import org.improving.siege.domain.EquippableWorldItem;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.exceptions.ItemNotFoundGameException;
import org.improving.siege.exceptions.NoTargetGameException;
import org.improving.siege.exceptions.SlotTakenGameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class EquipCommand extends AliasedCommand {
    public EquipCommand(GameContext context, InputOutput io) {
        super(context, io);
    }

    @Override
    public Stream<String> getAliases() {
        return Stream.of("equip");
    }

    @Override
    public String getHelpText() {
        return "Equips an item from your inventory.";
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
        var slot = "";
        try {
            var params = getParameters(input);
            if (params.length == 0) throw new NoTargetGameException();
            var item = context.getPlayer().find(EquippableWorldItem.class, params[0]);
            if ((item instanceof Equippable) == false) {
                io.displayAlert("You cannot equip " + item.toString() + ".");
                return;
            }
            slot = item.getEquipmentType();
            context.getPlayer().equip(item);
            io.displayAlert("You equip " + item.toString() + ".");
        } catch (ItemNotFoundGameException ex) {
            io.displayAlert("You aren't carrying that right now.");
        } catch (SlotTakenGameException ex) {
            io.displayAlert("You already equipping a " + slot + ".");
        }
    }
}
