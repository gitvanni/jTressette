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
import it.unicam.cs.tressette.core.TressetteAction;
import it.unicam.cs.tressette.core.TressetteGameState;
import it.unicam.cs.tressette.core.TressetteStrategy;

import java.util.List;
import java.util.stream.Collectors;

import static it.unicam.cs.tressette.cards.CardsUtil.*;

//strategy to use in offense, in order to check the moves with the most dominant suit
public class DominantSuitStrategy implements TressetteStrategy {
    @Override
    public TressetteAction chooseAction(TressetteGameState state) {
        return null;
    }

    @Override
    public List<TressetteAction> getActions(TressetteGameState state) {

        TressetteGameState other = (TressetteGameState) state.deepCopy();
        int currentPlayer = other.getCurrentPlayer();
        Hand playerHand = other.getPlayerHands().get(currentPlayer);
        Suit dominant = getMostFrequentSuit(playerHand.getCards());

        List<Card> playedCards = other.getTable().getPlayerTakes(0);
        playedCards.addAll(other.getTable().getPlayerTakes(1));
        List<Card> dominantCards = playerHand.getCardsBySuit(dominant);
        /*if(containsTwoThreeOrAce(dominantCards)){
            return dominantCards.stream().map(c-> new TressetteAction(currentPlayer,c)).collect(Collectors.toList());
        }



         */

        boolean hasAce = dominantCards.stream().anyMatch(card -> card.getRank() == Rank.ACE);
        boolean hasTwo = dominantCards.stream().anyMatch(card -> card.getRank() == Rank.TWO);
        boolean hasThree = dominantCards.stream().anyMatch(card -> card.getRank() == Rank.THREE);



        //Se non ho due tre o asso nel seme dominante, scarto il ramo
        if(!containsTwoThreeOrAce(dominantCards))
            return null;

        //se ho l'asso e tre e due non sono usciti, scarto il ramo
        if(hasAce&&!containsThreeOfSuit(playedCards,dominant)&&!containsTwoOfSuit(playedCards,dominant))
            return null;
        //se ho il due ma il tre non è uscito e non ce l'ho in mano, scarto il ramo
        if(hasTwo&&!hasThree&&!containsThreeOfSuit(playedCards,dominant))
            return null;
        return dominantCards.stream().map(c-> new TressetteAction(currentPlayer,c)).collect(Collectors.toList());

    }

    public boolean containsTwoThreeOrAce(List<Card> cards) {
        return cards.stream()
                .anyMatch(card ->
                        card.getRank() == Rank.ACE ||
                                card.getRank() == Rank.TWO ||
                                card.getRank() == Rank.THREE
                );
    }
}
