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

package it.unicam.cs.tressette.strategies;

import it.unicam.cs.tressette.cards.Card;
import it.unicam.cs.tressette.cards.Hand;
import it.unicam.cs.tressette.cards.Rank;
import it.unicam.cs.tressette.cards.Suit;
import it.unicam.cs.tressette.core.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//Strategia per quando sono di mano
public class OffenseStrategy implements TressetteStrategy {
    @Override
    public TressetteAction chooseAction(TressetteGameState state) {
        TressetteGameState other = (TressetteGameState) state.deepCopy();
        int currentPlayer = other.getCurrentPlayer();
        Hand playerHand = other.getPlayerHands().get(currentPlayer);
        Suit dominant = getMostFrequentSuit(playerHand.getCards());

        List<Card> dominantCards = playerHand.getCardsBySuit(dominant);

        boolean hasTwo = dominantCards.stream().anyMatch(card -> card.getRank() == Rank.TWO);
        boolean hasThree = dominantCards.stream().anyMatch(card -> card.getRank() == Rank.THREE);
        boolean hasAce = dominantCards.stream().anyMatch(card -> card.getRank() == Rank.ACE);

        // Check if the required cards have been played
        boolean twoPlayed = state.getTable().getPlayerTakes(0).stream().anyMatch(card -> card.getRank() == Rank.TWO) &&
                state.getTable().getPlayerTakes(1).stream().anyMatch(card -> card.getRank() == Rank.TWO);
        boolean threePlayed = state.getTable().getPlayerTakes(0).stream().anyMatch(card -> card.getRank() == Rank.THREE) &&
                state.getTable().getPlayerTakes(1).stream().anyMatch(card -> card.getRank() == Rank.THREE);
        boolean acePlayed = state.getTable().getPlayerTakes(0).stream().anyMatch(card -> card.getRank() == Rank.ACE) &&
                state.getTable().getPlayerTakes(1).stream().anyMatch(card -> card.getRank() == Rank.ACE);;

                Card cardo;
        // Play logic based on the rules provided
        if (hasAce && twoPlayed && threePlayed) {
            cardo = dominantCards.stream().filter(card -> card.getRank() == Rank.ACE).findFirst().orElse(null);
            return new TressetteAction(currentPlayer,cardo);
        } else if (hasTwo && threePlayed) {
            cardo = dominantCards.stream().filter(card -> card.getRank() == Rank.TWO).findFirst().orElse(null);
            return new TressetteAction(currentPlayer,cardo);
        } else if (hasThree && !twoPlayed && !acePlayed) {
            cardo = dominantCards.stream().filter(card -> card.getRank() == Rank.THREE).findFirst().orElse(null);
            return new TressetteAction(currentPlayer,cardo);
        }

        // If no conditions are met, return null or a default card
        return new TressetteAction(currentPlayer,getCardWithHighestRank(dominantCards));
    }

    @Override
    public List<TressetteAction> getActions(TressetteGameState state) {
        return null;
    }


    private  Suit getMostFrequentSuit(List<Card> cards) {
        Map<Suit, Long> suitCount = cards.stream()
                .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting()));

        // Find the suit with the maximum count
        Optional<Suit> mostFrequentSuit = suitCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

        return mostFrequentSuit.orElse(null); // Return null if no suit found
    }

    public Card getCardWithHighestRank(List<Card> cards) {
        return cards.stream()
                .max(Comparator.comparing(Card::getRank,Rank.BY_VALUE))
                .orElse(null);  // Returns null if the list is empty
    }
}
