package org.improving.siege.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item implements StatisticModifier {
    protected String id;
    protected String name;
    protected int strength;
    protected int dexterity;
    protected int constitution;
    protected List<String> aliases = new ArrayList<>();

    public Item(String name, String... aliases) {
        this(name,0,0,0,aliases);
    }
    public Item(String name, int strength, int dexterity, int constitution, String... aliases) {
        this.name = name;
        this.aliases.addAll(Arrays.asList(aliases));
        this.strength = strength;
        this.constitution = constitution;
        this.dexterity = dexterity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        var baseName = this.getName();
        if (strString.length() > 0) baseName += " (" + strString + ")";
        if (dexString.length() > 0) baseName += " (" + dexString + ")";
        if (conString.length() > 0) baseName += " (" + conString + ")";
        return baseName;
    }
    public List<String> getAliases() {
        return aliases;
    }


    public boolean matchesNameOrAlias(String name) {
        return this.getName().equalsIgnoreCase(name) ||
                this.getAliases().stream().anyMatch(name::equalsIgnoreCase);
    }
}
