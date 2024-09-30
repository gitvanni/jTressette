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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HigherCardStrategy implements TressetteStrategy {

    @Override
    public TressetteAction chooseAction(TressetteGameState state) {
        int currentPlayer=state.getCurrentPlayer();
        if(state.getTable().isEmpty()){
            List<Card> cards = state.getPlayerHands().get(currentPlayer).getCards();
            Suit dominant = cards.stream()
                    .collect(Collectors.groupingBy(Card::getSuit, Collectors.counting())) // Group by suit and count
                    .entrySet().stream()
                    .max(Comparator.comparingLong(Map.Entry::getValue)) // Find the entry with the max count
                    .map(Map.Entry::getKey) // Get the suit from the entry
                    .orElse(null);
            Card toReturn = cards.stream()
                    .filter(card -> card.getSuit() == dominant) // Filter cards by the given suit
                    .max(Comparator.comparing(Card::getRank,Rank.BY_VALUE)) // Find the card with the highest rank
                    .orElse(null); // Return null if no cards are found for that suit
            return new TressetteAction(currentPlayer,toReturn);
        }
        Suit onTheTable = state.getTable().getCardsOnTheTable().get(0).getSuit();
        List<Card> cards = state.getPlayerHands().get(currentPlayer).getCards();
        Card toReturn = cards.stream()
                .filter(card -> card.getSuit() == onTheTable) // Filter cards by the given suit
                .max(Comparator.comparing(Card::getRank)) // Find the card with the highest rank
                .orElse(null); // Return null if no cards are found for that suit
        return new TressetteAction(currentPlayer,toReturn);
    }
}
