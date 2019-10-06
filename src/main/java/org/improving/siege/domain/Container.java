package org.improving.siege.domain;

import org.improving.siege.exceptions.ItemNotFoundGameException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class Container extends Item {
    public static <T> T as(Class<T> clazz, Object o){
        if(clazz.isInstance(o)){
            return clazz.cast(o);
        }
        return null;
    }


    protected final List<Item> items = new ArrayList<>();

    public Container(String name, String... aliases) {
        super(name, aliases);
    }

    public Container(String name, int strength, int dexterity, int constitution, String... aliases) {
        super(name, strength, dexterity, constitution, aliases);
    }


    public List<Item> getItems() {
        return items;
    }

    public <T extends Item> T findByClass(Class<T> clazz, String name) throws ItemNotFoundGameException {
        return findAll(clazz)
                .filter(e -> e.matchesNameOrAlias(name))
                .findFirst()
                .orElseThrow(ItemNotFoundGameException::new);
    }

    public Item find(String name) throws ItemNotFoundGameException {
        return findAll()
                .filter(e -> e.matchesNameOrAlias(name))
                .findFirst()
                .orElseThrow(ItemNotFoundGameException::new);
    }

    public Stream<Item> findAll() {
        return this.items.stream();
    }
    public <T extends Item> Stream<T> findAll(Class<T> clazz) {
        return findAll()
                .map(e -> as(clazz, e))
                .filter(e -> e != null);
    }
}
