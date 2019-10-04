package org.improving.siege.domain;

public class StatisticItem extends Item implements StatisticModifier, Equippable {
    private int strength;
    private int dexterity;
    private int constitution;

    public StatisticItem(String name, int strength, int dexterity, int constitution, String... aliases) {
        super(name, aliases);
        this.strength = strength;
        this.constitution = constitution;
        this.dexterity = dexterity;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public int getConstitution() {
        return constitution;
    }

    @Override
    public int getDexterity() {
        return dexterity;
    }

    @Override
    public String toString() {
        var strString = "";
        var dexString = "";
        var conString = "";
        if (strength > 0) strString += "+" + strength + " STR";
        if (strength < 0) strString += "-" + strength + " STR";
        if (dexterity > 0) dexString += "+" + dexterity + " DEX";
        if (dexterity < 0) dexString += "-" + dexterity + " DEX";
        if (constitution > 0) conString += "+" + constitution + " CON";
        if (constitution < 0) conString += "-" + constitution + " CON";
        var baseName = super.toString();
        if (strString.length() > 0) baseName += " (" + strString + ")";
        if (dexString.length() > 0) baseName += " (" + dexString + ")";
        if (conString.length() > 0) baseName += " (" + conString + ")";
        return baseName;
    }
}
