package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class InventoryCommand extends AliasedCommand {
    public InventoryCommand(GameContext context, InputOutput io) {
        super(context, io);
    }

    @Override
    public Stream<String> getAliases() {
        return Stream.of("inventory","inv","i");
    }

    @Override
    public void execute(String input) throws GameException {
        var p = context.getPlayer();
        io.displayText("STR " + p.getStrength() + " / DEX " + p.getDexterity() + " / CON " + p.getConstitution());
        io.displayText("HP : " + p.getHitPoints() + "/" + p.getMaxHitPoints());
        for(var item : p.getEquipment()) {
            io.displayAlert(item.toString());
        }
        for(var item : p.getItems()) {
            io.displayIndent(item.toString());
        }
    }

    @Override
    public String getHelpText() {
        return "Displays all items you are carrying.";
    }
}
