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

package it.unicam.cs.tressette.cards;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CardsUtil {

    public static Card getCardWithHighestRank(List<Card> cards) {
        return cards.stream()
                .max(Comparator.comparing(Card::getRank,Rank.BY_VALUE))
                .orElse(null);  // Returns null if the list is empty
    }

    public static Suit getMostFrequentSuit(List<Card> cards) {
        Map<Suit, Long> suitCount = cards.stream()
                .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));

        // Find the suit with the maximum count
        Optional<Suit> mostFrequentSuit = suitCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

        return mostFrequentSuit.orElse(null); // Return null if no suit found
    }

    public static Card getCardWithLowestRank(List<Card> cards) {
        return cards.stream()
                .min(Comparator.comparing(Card::getRank,Rank.BY_VALUE))
                .orElse(null);  // Returns null if the list is empty
    }

    public static boolean containsThreeOfSuit(List<Card> cards, Suit suit) {
        return cards.stream()
                .anyMatch(card -> card.getRank() == Rank.THREE && card.getSuit() == suit);
    }

    public static boolean containsTwoOfSuit(List<Card> cards, Suit suit) {
        return cards.stream()
                .anyMatch(card -> card.getRank() == Rank.THREE && card.getSuit() == suit);
    }

    public static List<Card> filterHigherRankSameSuit(List<Card> cards, Card specificCard) {
        Suit suit = specificCard.getSuit();
        Rank rank = specificCard.getRank();

        return cards.stream()
                .filter(card -> card.getSuit() == suit && card.getRank().getValue() > rank.getValue())
                .collect(Collectors.toList());
    }

    public static List<Card> filterLowerRankSameSuit(List<Card> cards, Card specificCard) {
        Suit suit = specificCard.getSuit();
        Rank rank = specificCard.getRank();

        return cards.stream()
                .filter(card -> card.getSuit() == suit && card.getRank().getValue() < rank.getValue())
                .collect(Collectors.toList());
    }
}
