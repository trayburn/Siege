package org.improving.siege.domain;

public class Card {
    private final Suits suit;
    private final Faces face;

    public Card(Suits suit, Faces face) {
        this.suit = suit;
        this.face = face;
    }

    public Suits getSuit() {
        return suit;
    }

    public Faces getFace() {
        return face;
    }

    @Override
    public String toString() {
        return getFace().toString() + " of " + getSuit().toString();
    }
}
