package com.example.cardswar;

public class Card implements Comparable<Card> {
    static final char[] symbolChars = {'h', 's', 'c', 'd'};
    private final cardSymbol symbol;
    private final int number;

    public Card(cardSymbol symbol, int number) {
        this.symbol = symbol;
        this.number = number;
    }

    public cardSymbol getSymbol() {
        return symbol;
    }

    public int getNumber() {
        return number;
    }

    public String getCardFileName() {
        return "img_card_" + this.getNumber() + symbolChars[this.getSymbol().ordinal()] + "";
    }


    @Override
    public String toString() {
        return "Card{" +
                "symbol=" + symbol +
                ", number=" + number +
                ", fileName=" + this.getCardFileName() +
                '}';
    }

    @Override
    public int compareTo(Card o) {
        return this.getNumber() - o.getNumber();
    }
}
