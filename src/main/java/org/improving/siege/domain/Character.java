package org.improving.siege.domain;

import org.improving.siege.exceptions.ItemNotFoundGameException;
import org.improving.siege.exceptions.NotCarriedGameException;
import org.improving.siege.exceptions.NotEquippedGameException;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Container implements StatisticModifier {
    private final String name;
    private Location location;
    private List<Item> equipment = new ArrayList<>();


    private int hitPoints;
    private int maxHitPoints;

    public Character(String name, int strength, int dexterity, int constitution) {
        super(name, strength, dexterity, constitution);
        this.name = name;
        initSecondaryStatistics();
    }

    private void initSecondaryStatistics() {
        this.hitPoints = this.getConstitution() * 10;
        this.maxHitPoints = this.hitPoints;
    }

    private void recalculateSecondaryStatistics() {
        this.maxHitPoints = this.getConstitution() * 10;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getStrength() {
        return strength +
                equipment.stream()
                        .filter(e -> e instanceof StatisticModifier)
                        .mapToInt(e -> ((StatisticModifier) e).getStrength())
                        .sum();
    }

    @Override
    public int getConstitution() {
        return constitution +
                equipment.stream()
                        .filter(e -> e instanceof StatisticModifier)
                        .mapToInt(e -> ((StatisticModifier) e).getConstitution())
                        .sum();
    }

    @Override
    public int getDexterity() {
        return dexterity +
                equipment.stream()
                        .filter(e -> e instanceof StatisticModifier)
                        .mapToInt(e -> ((StatisticModifier) e).getDexterity())
                        .sum();
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Iterable<Item> getEquipment() {
        return equipment;
    }

    public void equip(Item item) throws NotCarriedGameException {
        if (this.getItems().contains(item) == false)
            throw new NotCarriedGameException();
        equipment.add(item);
        this.getItems().remove(item);
        recalculateSecondaryStatistics();
    }

    public void unequip(Item item) throws NotEquippedGameException {
        if (this.equipment.contains(item) == false)
            throw new NotEquippedGameException();
        this.getItems().add(item);
        equipment.remove(item);
        recalculateSecondaryStatistics();
    }

    public Item findEquipment(String name) throws ItemNotFoundGameException {
        return this.equipment.stream()
                .filter(e -> e.matchesNameOrAlias(name))
                .findFirst()
                .orElseThrow(ItemNotFoundGameException::new);
    }
}
