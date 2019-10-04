package org.improving.siege.commands;

import org.improving.siege.GameContext;
import org.improving.siege.Roller;
import org.improving.siege.domain.Enemy;
import org.improving.siege.domain.Player;
import org.improving.siege.exceptions.ExitGameException;
import org.improving.siege.exceptions.GameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class AttackCommand extends AliasedCommand {
    private final Roller roller;
    private final int damageMultiplier = 8;
    private final int toHitMultiplier = 10;
    private final int baseToHit = 50;

    public AttackCommand(Roller roller, GameContext gameContext, InputOutput io) {
        super(gameContext, io);
        this.roller = roller;
    }

    @Override
    public Stream<String> getAliases() {
        return Stream.of("attack", "a", "swing", "stab");
    }

    @Override
    public String getHelpText() {
        return "Attack an enemy, if present.";
    }

    @Override
    public void execute(String input)  throws GameException {
        var player = context.getPlayer();
        var enemy = context.getPlayer().getLocation().getEnemy();

        if (enemy == null) {
            io.displayText("There is nothing to attack here.");
            return;
        }

        playerAttackEnemy(player, enemy);
        if (context.getPlayer().getLocation().getEnemy() != null)
            enemyAttackPlayer(player, enemy);
    }

    private void playerAttackEnemy(Player player, Enemy enemy) {
        var hitDifference = player.getDexterity() - enemy.getDexterity();
        var target = baseToHit + (hitDifference * toHitMultiplier);
        var roll = roller.roll();
        if (roll <= target) {
            enemy.setHitPoints(enemy.getHitPoints() - (player.getStrength() * damageMultiplier));
            if (enemy.getHitPoints() > 0) {
                io.displayText("You hit " + enemy.getName() + "! (" + enemy.getHitPoints() + "/" + enemy.getMaxHitPoints() + ")");
            } else {
                io.displayText("You have slain " + enemy.getName());
                context.getPlayer().getLocation().setEnemy(null);
                if (enemy.getItems().size() > 0) {
                    io.displayAlert("You loot:");
                    enemy.getItems().stream().forEach(e -> io.displayAlert(e.getName()));
                    player.getItems().addAll(enemy.getItems());
                }
            }
        } else {
            io.displayText("You missed.");
        }
    }
    private void enemyAttackPlayer(Player player, Enemy enemy) throws ExitGameException {
        var hitDifference = enemy.getDexterity() - player.getDexterity();
        var target = baseToHit + (hitDifference * toHitMultiplier);
        var roll = roller.roll();
        if (roll <= target) {
            player.setHitPoints(player.getHitPoints() - (enemy.getStrength() * damageMultiplier));
            if (player.getHitPoints() > 0) {
                io.displayText("You've been hit " + player.getName() + "! (" + player.getHitPoints() + "/" + player.getMaxHitPoints() + ")");
            } else {
                io.displayText("You have been slain by " + enemy.getName());
                throw new ExitGameException();
            }
        } else {
            io.displayText(enemy.getName() + " missed.");
        }
    }
}
