package org.improving.siege.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item {
    private String name;
    private List<String> aliases = new ArrayList<>();

    public Item(String name, String... aliases) {
        this.name = name;
        this.aliases.addAll(Arrays.asList(aliases));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public List<String> getAliases() {
        return aliases;
    }


    public boolean matchesNameOrAlias(String name) {
        return this.getName().equalsIgnoreCase(name) ||
                this.getAliases().stream().anyMatch(name::equalsIgnoreCase);
    }
}
