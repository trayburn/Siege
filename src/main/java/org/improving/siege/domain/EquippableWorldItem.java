package org.improving.siege.domain;

public class EquippableWorldItem extends WorldItem implements Equippable {

    private String equipmentType;

    public EquippableWorldItem(String name, String... aliases) {
        super(name, aliases);
    }

    public EquippableWorldItem(String name, String type, int strength, int dexterity, int constitution, String... aliases) {
        super(name, strength, dexterity, constitution, aliases);
        this.equipmentType = type;
    }

    @Override
    public String getEquipmentType() {
        return equipmentType;
    }

    @Override
    public void setEquipmentType(String type) {
        equipmentType = type;
    }

    @Override
    public String toString() {
        var suffix = "";
        if (equipmentType != null && equipmentType.length() > 0) {
            suffix += " [" + equipmentType + "]";
        }
        return super.toString() + suffix;
    }
}
