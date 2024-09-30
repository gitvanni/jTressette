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
import java.util.Collections;
import java.util.List;

/**
 * This class represents a deck of cards.
 * It is used to create and manage a collection of cards.
 * A deck gets composed by adding to it all the possible combinations of suits and ranks
 * of the cards.
 *
 */
public class Deck {
    private final List<Card> cards;

    /**
     * Creates a new deck of cards with all the possible combinations of suits and ranks
     */
    public Deck() {
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public Deck(Deck otherDeck) {
        this.cards = new ArrayList<>();
        for (Card card : otherDeck.cards) {
            this.cards.add(card.deepCopy()); // Deep copy each card
        }
    }

    public Deck deepCopy() {
        return new Deck(this); // Use the copy constructor
    }

    /**
     * This method shuffles the deck of cards
     */
    public void shuffle(){
        Collections.shuffle(getCards());
    }

    /**
     * Returns the list of cards composing this deck
     *
     * @return the list of cards composing this deck
     */
    private List<Card> getCards() {
        return this.cards;
    }

    /**
     * This method is used to deal the card on top of the deck.
     *
     * @return the card on top of the deck
     * @throws IllegalStateException if the deck is empty
     */
    public Card deal(){
        if(isEmpty())
            throw new IllegalStateException("Deck is empty");
        return cards.remove(0);
    }

    /**
     * This method is used to deal a specified amount of cards, picking them from the top of the deck
     *
     * @param amount the amount of cards to deal
     * @return a list of cards to deal
     * @throws IllegalStateException if the deck is empty or if the amount of cards to deal exceeds the
     * amount of cards present in the deck
     */
    public List<Card> deal(int amount) {
        if (isEmpty()) {
            throw new IllegalStateException("No more cards in the deck");
        }
        if (amount > cards.size()) {
            throw new IllegalStateException("Cannot deal more cards than are in the deck");
        }
        List<Card> dealtCards = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            dealtCards.add(cards.remove(0));
        }
        return dealtCards;
    }

    /**
     * Returns true if the deck is empty
     *
     * @return true if the deck is empty
     *         false otherwise
     */
    public boolean isEmpty(){
        return cards.isEmpty();
    }
}
