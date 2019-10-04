package org.improving.siege;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Roller {
    private final Random random;

    public Roller(Random random) {
        this.random = random;
    }

    public int roll() {
        return random.nextInt(100) + 1;
    }
}
