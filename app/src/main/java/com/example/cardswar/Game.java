package com.example.cardswar;

import android.util.Log;

public class Game {
    public static final int P1 = 0;
    public static final int P2 = 1;
    public static final int TIE = 2;
    @SuppressWarnings("SpellCheckingInspection")
    public static final String TAG = "pddd";
    public static final int MIN_ROW_BONUS = 3;
    private int lastWinner;


    public int row;
    private final int[] scores;
    private final Deck deck;

    public Game() {
        this.scores = new int[2];
        this.deck = new Deck();
        row = 0;
        lastWinner = -1;
    }

    public static int checkStrongerCard(Card c1, Card c2) {
        if (c1.compareTo(c2) > 0)
            return P1;
        else return P2;
    }

    public int gameWinner() {
        if (scores[P1] == scores[P2]) return TIE;
        return (scores[P1] > scores[P2]) ? P1 : P2;
    }

    public Deck getDeck() {
        return deck;
    }

    public Card[] playTurn() {

        Log.d(TAG, "playTurn: " + this.deck.getDeck().size());
        Card card1 = this.getDeck().getTopCard();
        Card card2 = this.getDeck().getTopCard();
        int winner = Game.checkStrongerCard(card1, card2);
        if (lastWinner == winner)
            row++;
        else {
            if (row >= MIN_ROW_BONUS) this.scores[lastWinner]+=row-1;
            row = 1;
        }

        if (winner == Game.P1)
            this.scores[0] += 1;
        else if (winner == Game.P2)
            this.scores[1] += 1;
        lastWinner = winner;
        return new Card[]{card1, card2};
    }

    public int[] getScores() {
        return scores;
    }

    public boolean isFinished() {
        return this.getDeck().isEmpty();
    }

    public int getProgress() {
        return Deck.DECK_CAPACITY - this.deck.getRemaining();
    }

    public int getRow() {
        return row;
    }
}

