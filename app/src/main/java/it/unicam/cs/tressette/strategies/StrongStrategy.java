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

import it.unicam.cs.tressette.cards.*;
import it.unicam.cs.tressette.core.TressetteAction;
import it.unicam.cs.tressette.core.TressetteGameState;
import it.unicam.cs.tressette.core.TressetteStrategy;
import static it.unicam.cs.tressette.cards.CardsUtil.*;

import java.util.Comparator;
import java.util.List;

public class StrongStrategy implements TressetteStrategy {
    @Override
    public TressetteAction chooseAction(TressetteGameState state) {
        TressetteGameState other = (TressetteGameState) state.deepCopy();
        int currentPlayer = other.getCurrentPlayer();
        Hand playerHand = other.getPlayerHands().get(currentPlayer);
        Suit dominant = CardsUtil.getMostFrequentSuit(playerHand.getCards());
        List<Card> dominantCards = playerHand.getCardsBySuit(dominant);

        List<Card> playedCards = other.getTable().getPlayerTakes(0);
        playedCards.addAll(other.getTable().getPlayerTakes(1));




        boolean hasTwo = dominantCards.stream().anyMatch(card -> card.getRank() == Rank.TWO);
        boolean hasThree = dominantCards.stream().anyMatch(card -> card.getRank() == Rank.THREE);
        boolean hasAce = dominantCards.stream().anyMatch(card -> card.getRank() == Rank.ACE);
        Card cardo;
        //se ho un asso ed ho anche due e tre gioco asso
        if(hasAce&&hasTwo&&hasThree){
            cardo = dominantCards.stream().filter(card -> card.getRank() == Rank.ACE).findFirst().orElse(null);
            return new TressetteAction(currentPlayer,cardo);
        }else if(hasAce&&containsThreeOfSuit(playedCards,dominant)&&containsTwoOfSuit(playedCards,dominant)){
            cardo = dominantCards.stream().filter(card -> card.getRank() == Rank.ACE).findFirst().orElse(null);
            return new TressetteAction(currentPlayer,cardo);
        }else if(hasTwo&&(hasThree||containsThreeOfSuit(playedCards,dominant))){
                cardo = dominantCards.stream().filter(card -> card.getRank() == Rank.TWO).findFirst().orElse(null);
            return new TressetteAction(currentPlayer,cardo);
        }

        return null;
    }

    @Override
    public List<TressetteAction> getActions(TressetteGameState state) {
        return null;
    }



}
