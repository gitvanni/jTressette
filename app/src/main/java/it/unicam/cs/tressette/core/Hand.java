/*
 * MIT License
 *
 * Copyright (c) 2024 gitvanni
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package it.unicam.cs.tressette.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a hand of cards held by a player
 * in a card game
 */
public class Hand {
    private final List<Card> cards;
    private final int playerIndex;

    /**
     * Constructs a new hand without any cards in it
     */
    public Hand(int playerIndex){
        this(new ArrayList<>(), playerIndex);
    }
    /**
     * Constructs a new hand of cards with the given list of cards.
     *
     * @param cards       the list of cards to include in this hand
     * @param playerIndex the index of the player which holds this hand
     */
    public Hand(List<Card> cards, int playerIndex) {
        this.cards = cards;
        this.playerIndex = playerIndex;
    }

    public Hand(Hand otherHand) {
        this.playerIndex = otherHand.playerIndex; // Copy the player index
        this.cards = new ArrayList<>();
        for (Card card : otherHand.cards) {
            this.cards.add(card.deepCopy()); // Deep copy each card
        }
    }

    public Hand deepCopy() {
        return new Hand(this); // Use the copy constructor
    }

    /**
     * Returns the cards that compose this hand
     *
     * @return the cards that compose this hand
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * This method sorts the hand based off ranks and suits
     */
    public void sort(){

    }

    public void addToHand(List<Card> cards){
        this.cards.addAll(cards);
    }

    public void addToHand(Card card){
        this.cards.add(card);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hand hand)) return false;
        return playerIndex == hand.playerIndex && Objects.equals(cards, hand.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards, playerIndex);
    }
}
