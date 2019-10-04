package org.improving.siege;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Siege {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(SpringContext.class);
        var game = context.getBean(Game.class);

        game.run();
    }
}
