package org.improving.siege.domain;

import org.improving.siege.exceptions.ItemNotFoundGameException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Container {
    protected final List<Item> items;

    public Container(Item... items) {
        this.items = new ArrayList<>();
        this.items.addAll(Arrays.asList(items));
    }

    public List<Item> getItems() {
        return items;
    }

    public Item find(String name) throws ItemNotFoundGameException {
        return this.items.stream()
                .filter(e -> e.matchesNameOrAlias(name))
                .findFirst()
                .orElseThrow(ItemNotFoundGameException::new);
    }
}
