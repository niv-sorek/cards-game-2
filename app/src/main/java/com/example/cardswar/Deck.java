package com.example.cardswar;

import android.util.Log;

import java.util.Collections;
import java.util.Stack;

public class Deck {
    public static final int CARDS_NUMBERS = 13;
    public static final int FIRST_CARD_NUM = 1;
    private final Stack<Card> cards;

    public Deck() {
        this.cards = new Stack<>();
        for (int i = FIRST_CARD_NUM; i <= CARDS_NUMBERS; i++) {
            for (cardSymbol c : cardSymbol.values()) {
                this.cards.push(new Card(c, i));
            }
            Collections.shuffle(this.cards);
        }
        Log.d(Game.TAG, "Deck:  " + this.cards.size());
    }

    public Card getTopCard() {
        if (!this.cards.isEmpty())
            return this.cards.pop();
        else return null;
    }

    public boolean isEmpty() {
        return this.cards.isEmpty();
    }

    public Stack<Card> getDeck() {
        return this.cards;
    }
}
