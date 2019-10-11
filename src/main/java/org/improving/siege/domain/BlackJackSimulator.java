package org.improving.siege.domain;

import org.improving.siege.exceptions.InvalidCommandGameException;
import org.improving.siege.io.InputOutput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class BlackJackSimulator {
    private final InputOutput io;
    private final Random random = new Random();
    private boolean started = false;
    private boolean complete = false;
    private int win, lose, draw = 0;
    private List<Card> deck;
    private List<Card> discard;
    private List<Card> hand;
    private List<Card> dealerHand;
    private Card dealerShowCard;
    private List<Faces> faces = new ArrayList<Faces>(Arrays.asList(new Faces[]{Faces.Ten, Faces.Jack, Faces.Queen, Faces.King}));

    public BlackJackSimulator(InputOutput io) {
        this.io = io;
    }

    public boolean isFaceCard(Card card) {
        return faces.contains(card.getFace());
    }

    public boolean isStarted() {
        return started;
    }

    public Iterable<Card> getHand() {
        return hand;
    }

    public Card getDealerShowCard() {
        return dealerShowCard;
    }

    public void start() throws InvalidCommandGameException {
        if (started) throw new InvalidCommandGameException();

        if (deck == null) {
            newGame();
        }
    }

    public void hit() {
        hand.add(draw());
    }

    private Card draw() {
        var cardIndex = random.nextInt(deck.size());
        var card = deck.get(cardIndex);
        deck.remove(card);
        return card;
    }

    public void output(InputOutput io) {
        io.displayText("Your hand is:");
        for (var card : this.getHand()) {
            io.displayText(card.toString());
        }
        io.displayAlert("The dealer is showing:");
        io.displayText(this.getDealerShowCard().toString());
        if (this.isFaceCard(this.getDealerShowCard()) ||
                this.getDealerShowCard().getFace() == Faces.Ace) {
            io.displayText("Insurance is for suckers");
        }
        if (complete) {
            var myCount = count(hand);
            var dealerCount = count(dealerHand);
            io.displayAlert("Your total is : " + myCount);
            io.displayAlert("Dealer total is : " + dealerCount);
            for (var card : dealerHand) {
                io.displayText("  " + card.toString());
            }
            if ((myCount < dealerCount && dealerCount <= 21) || myCount > 21) {
                lose += 1;
                io.displayAlert("You Lose!");
            } else if ((myCount > dealerCount && myCount <= 21) || dealerCount > 21) {
                win += 1;
                io.displayAlert("You Win!");
            } else {
                draw += 1;
                io.displayAlert("You push.");
            }
            io.displayAlert("You have won " + win + ", lost " + lose + ", and drawn " + draw + " games total.");
        }
    }

    public void stand() {
        var dealerCount = count(dealerHand);
        while (dealerCount < 17) {
            dealerHand.add(draw());
            dealerCount = count(dealerHand);
        }
        complete = true;
    }

    private int count(List<Card> hand) {
        return hand.stream().mapToInt(e -> faceToInt(e.getFace())).sum();
    }

    private int faceToInt(Faces face) {
        switch (face) {
            case Two:
                return 2;
            case Three:
                return 3;
            case Four:
                return 4;
            case Five:
                return 5;
            case Six:
                return 6;
            case Seven:
                return 7;
            case Eight:
                return 8;
            case Nine:
                return 9;
            case Ten:
            case Queen:
            case Jack:
            case King:
                return 10;
            case Ace:
                return 11;
            default:
                return 0;
        }
    }

    public void newGame() {
        deck = new ArrayList<>();
        hand = new ArrayList<>();
        discard = new ArrayList<>();
        dealerHand = new ArrayList<>();
        for (var face : Faces.values()) {
            for (var suit : Suits.values()) {
                deck.add(new Card(suit, face));
            }
        }
        hand.add(this.draw());
        hand.add(this.draw());
        dealerHand.add(this.draw());
        dealerHand.add(this.draw());
        dealerShowCard = dealerHand.get(1);
        started = true;
        complete = false;
    }

    public boolean isComplete() {
        return complete;
    }
}
