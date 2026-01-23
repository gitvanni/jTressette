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
import it.unicam.cs.tressette.cards.Rank;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class StrategiaDiRisposta implements TressetteStrategy {
    @Override
    public TressetteAction chooseAction(TressetteGameState state) {
      return null;
    }


    @Override
    public List<TressetteAction> getActions(TressetteGameState state) {
        List<TressetteAction> actions = new ArrayList<>();

        TressetteGameState other = (TressetteGameState) state.deepCopy();
        int currentPlayer = other.getCurrentPlayer();

        Card firstPlayerCard = other.getTable().getCardsOnTheTable().get(0);
        Suit firstPlayerSuit = firstPlayerCard.getSuit();

        Hand playerHand = other.getPlayerHands().get(currentPlayer);
        List<Card> cardsOfSameSuit = playerHand.getCardsBySuit(firstPlayerSuit);

        List<Card> playedCards = other.getTable().getPlayerTakes(0);
        playedCards.addAll(other.getTable().getPlayerTakes(1));

        if (!cardsOfSameSuit.isEmpty()) {
            // Se il giocatore ha carte dello stesso seme del primo giocatore, deve rispondere
            actions.addAll(suggestSameSuitActions(cardsOfSameSuit,firstPlayerCard,currentPlayer));
        } else {
            // Se il giocatore non ha carte dello stesso seme, può giocare qualsiasi altra carta
            //actions.addAll(suggestDifferentSuitActions(playerHand.getCards(),currentPlayer));
            return null;
        }

        return actions;
    }

    // Suggerisce le azioni quando il giocatore ha carte dello stesso seme
    private List<TressetteAction> suggestSameSuitActions(List<Card> cardsOfSameSuit,Card firstPlayerCard,int currentPlayer){
        List<TressetteAction> actions = new ArrayList<>();

        List<Card> winningCards = cardsOfSameSuit.stream()
                .filter(card -> card.compareTo(firstPlayerCard)>0)
                .collect(Collectors.toList());

        if (!winningCards.isEmpty()) {
            Card lowestWinningCard = getLowestCard(winningCards);
            actions.add(new TressetteAction(currentPlayer, lowestWinningCard));
        } else {
            Card lowestCard = getLowestCard(cardsOfSameSuit);
            actions.add(new TressetteAction(currentPlayer, lowestCard));
        }
        return actions;
    }

    // Suggerisce le azioni quando il giocatore non ha carte dello stesso seme
    private List<TressetteAction> suggestDifferentSuitActions(List<Card> hand,int currentPlayer) {
        List<TressetteAction> actions = new ArrayList<>();

        // Strategia per giocare una carta fuori seme
        // Gioca la carta più bassa per minimizzare la perdita
        Card lowestCard = getLowestCard(hand);
        if (lowestCard != null) {
            actions.add(new TressetteAction(currentPlayer, lowestCard));
        }

        return actions;
    }

    // Ottiene la carta con il rango più basso
    private Card getLowestCard(List<Card> cards) {
        return cards.stream().min(Comparator.comparing(Card::getRank)).orElse(null);
    }
}
