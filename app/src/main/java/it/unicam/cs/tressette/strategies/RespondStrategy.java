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
import it.unicam.cs.tressette.cards.Suit;
import it.unicam.cs.tressette.core.TressetteAction;
import it.unicam.cs.tressette.core.TressetteGameState;
import it.unicam.cs.tressette.core.TressetteStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static it.unicam.cs.tressette.cards.CardsUtil.*;

public class RespondStrategy implements TressetteStrategy {
    @Override
    public TressetteAction chooseAction(TressetteGameState state) {
        return null;
    }

    @Override
    public List<TressetteAction> getActions(TressetteGameState state) {
        TressetteGameState other = (TressetteGameState) state.deepCopy();
        int currentPlayer = other.getCurrentPlayer();

        Card played = other.getTable().getCardsOnTheTable().get(0);
        Suit toRespond = played.getSuit();

        Hand playerHand = other.getPlayerHands().get(currentPlayer);
        List<Card> playable = playerHand.getCardsBySuit(toRespond);

        if(playable.isEmpty())
            return null;

        //se devo rispondere, provo a fare una presa
        List<Card> response = filterHigherRankSameSuit(playable,played);
        if(!response.isEmpty()){
            return response.stream().map(c-> new TressetteAction(currentPlayer,c)).collect(Collectors.toList());
        }

        //se non posso fare una presa
        //List<Card> lowerCards = filterLowerRankSameSuit(playable,played);
        return null;
        //return  lowerCards.stream().map(c-> new TressetteAction(currentPlayer,c)).collect(Collectors.toList());
    }
}
