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

import java.util.Objects;

/**
 * This class describes a card with a seed and a rank used for playing card games.
 */
public class Card implements Comparable<Card> {
    private final Rank rank;
    private final Suit suit;

    /**
     * Constructs a new Card with the specified rank and suit
     *
     * @param rank the rank of the card
     * @param suit the suit of the card
     * @throws IllegalArgumentException if rank or suit is {@code null}
     */
    public Card(Rank rank, Suit suit) {
        if (rank == null) {
            throw new IllegalArgumentException("Rank cannot be null");
        }
        if (suit == null) {
            throw new IllegalArgumentException("Suit cannot be null");
        }
        this.rank = rank;
        this.suit = suit;
    }

    public Card(Card otherCard) {
        this.rank = otherCard.rank; // Enums are immutable, so just copy the reference
        this.suit = otherCard.suit; // Enums are immutable, so just copy the reference
    }

    public Card deepCopy() {
        return new Card(this); // Utilize the copy constructor
    }

    /**
     * Returns the rank of the card
     *
     * @return the rank of the card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns the suit of the card
     *
     * @return the suit of the card
     */
    public Suit getSuit() {
        return suit;
    }

    public boolean isOfSameSuit(Card other){
        return this.suit == other.getSuit();
    }



    //TODO: aggiungere java docs
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return Objects.equals(rank, card.rank) && Objects.equals(suit, card.suit);
    }

    //TODO: aggiungere javadoc
    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    //TODO:aggiungere java doc
    //TODO: non so se mi può essere utile il compare to..
    //TODO: forse mi conviene assegnare un valore ad ogni rank, in base alla potenza di presa della carta
    @Override
    public int compareTo(Card o) {
        return Integer.compare(rank.getValue(), o.rank.getValue());
    }

    @Override
    public String toString() {
        return "Card{" +
                "rank=" + rank +
                ", suit=" + suit +
                '}';
    }

    public boolean isFigureOrThreeOrTwo(){
        return rank == Rank.TWO ||
               rank == Rank.THREE ||
               rank == Rank.JACK ||
               rank == Rank.KNIGHT ||
               rank == Rank.KING;
    }
}

