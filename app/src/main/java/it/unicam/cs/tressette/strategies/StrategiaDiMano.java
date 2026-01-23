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

import java.util.*;
import java.util.stream.Collectors;

public class StrategiaDiMano implements TressetteStrategy {
    @Override
    public TressetteAction chooseAction(TressetteGameState state) {
    return null;

    }

    @Override
    public List<TressetteAction> getActions(TressetteGameState state) {
        TressetteGameState other = (TressetteGameState) state.deepCopy();
        int currentPlayer = other.getCurrentPlayer();
        Hand playerHand = other.getPlayerHands().get(currentPlayer);
        List<Card> playedCards = other.getTable().getPlayerTakes(0);
        playedCards.addAll(other.getTable().getPlayerTakes(1));


        List<TressetteAction> actions = new ArrayList<>();

        // Trova i semi dominanti nella mano
        Map<Suit, List<Card>> cardsBySuit = playerHand.getCards().stream()
                .collect(Collectors.groupingBy(Card::getSuit));

        // Per ogni seme, verifica quali carte alte sono disponibili e se puoi giocarle in sicurezza
        for (Suit suit : cardsBySuit.keySet()) {
            List<Card> cardsOfSuit = cardsBySuit.get(suit);
            boolean hasAce = containsRank(cardsOfSuit, Rank.ACE);
            boolean hasTwo = containsRank(cardsOfSuit, Rank.TWO);
            boolean hasThree = containsRank(cardsOfSuit, Rank.THREE);

            boolean isTwoPlayed = isCardPlayed(suit, Rank.TWO,playedCards);
            boolean isThreePlayed = isCardPlayed(suit, Rank.THREE,playedCards);

            // Se tutte le altre carte di questo seme sono già uscite, gioca una carta di questo seme per avere presa garantita
            if (areRemainingCardsInHand(suit, playerHand.getCards(),playedCards)) {
                Card highestCard = CardsUtil.getCardWithHighestRank(cardsOfSuit);
                actions.add(new TressetteAction(currentPlayer, highestCard));  // Gioca la carta più alta per massimizzare il guadagno
                continue;
            }

            // Se hai l'Asso e sono già usciti Due e Tre, gioca l'Asso
            if (hasAce && isTwoPlayed && isThreePlayed) {
                actions.add(new TressetteAction(currentPlayer, getCardByRank(cardsOfSuit, Rank.ACE)));
                continue; // Passa al prossimo seme
            }

            // Se hai il Due e il Tre è già uscito, gioca il Due
            if (hasTwo && isThreePlayed) {
                actions.add(new TressetteAction(currentPlayer, getCardByRank(cardsOfSuit, Rank.TWO)));
                continue;
            }

            // Se hai il Tre e né l'Asso né il Due sono usciti, gioca il Tre
            if (hasThree && !isTwoPlayed && !isCardPlayed(suit, Rank.ACE,playedCards)) {
                actions.add(new TressetteAction(currentPlayer, getCardByRank(cardsOfSuit, Rank.THREE)));
                continue;
            }

            // Se non puoi giocare carte alte, gioca la carta più bassa
            /*
            Card lowestCard = CardsUtil.getCardWithLowestRank(cardsOfSuit);
            if (lowestCard != null) {
                actions.add(new TressetteAction(currentPlayer, lowestCard));
            }*/
        }

        // Se non ci sono azioni dominanti, restituisci tutte le carte come possibili azioni
        if (actions.isEmpty()) {
            return null;
            //playerHand.getCards().forEach(card -> actions.add(new TressetteAction(currentPlayer, card)));
        }

        return actions;
    }

    private boolean isCardPlayed(Suit suit, Rank rank,List<Card> playedCards) {
        return playedCards.stream()
                .anyMatch(card -> card.getSuit() == suit && card.getRank() == rank);
    }

    // Controlla se una lista di carte contiene una certa carta
    private boolean containsRank(List<Card> cards, Rank rank) {
        return cards.stream().anyMatch(card -> card.getRank() == rank);
    }

    // Ottiene una carta di un determinato rango dalla lista di carte
    private Card getCardByRank(List<Card> cards, Rank rank) {
        return cards.stream().filter(card -> card.getRank() == rank).findFirst().orElse(null);
    }

    // Ottiene la carta di rango più basso
    private Card getLowestCard(List<Card> cards) {
        return cards.stream().min(Comparator.comparing(Card::getRank)).orElse(null);
    }

    // Ottiene la carta di rango più alto
    private Card getHighestCard(List<Card> cards) {
        return cards.stream().max(Comparator.comparing(Card::getRank)).orElse(null);
    }

    // Controlla se tutte le carte rimanenti di un seme sono nella mano del giocatore
    private boolean areRemainingCardsInHand(Suit suit, List<Card> hand,List<Card> playedCards) {
        // Tutte le carte possibili di questo seme
        List<Card> allCardsOfSuit = Arrays.stream(Rank.values())
                .map(rank -> new Card( rank,suit))
                .collect(Collectors.toList());

        // Verifica se tutte le carte di questo seme sono state giocate o sono nella mano del giocatore
        for (Card card : allCardsOfSuit) {
            if (!isCardPlayed(card.getSuit(), card.getRank(),playedCards) && !hand.contains(card)) {
                return false; // Se esiste una carta non giocata e non in mano, non è una presa garantita
            }
        }
        return true; // Se tutte le carte rimanenti sono in mano, è una presa garantita
    }
}
