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

import com.lostrucos.jabtbg.core.InformationSet;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TressetteInfoSet implements InformationSet<TressetteGameState,TressetteAction> {
    private int playerIndex;
    private Hand hand;
    private List<Card> playedCards;
    private List<Card> drawnCards;
    int numOfTurns;
    private List<TressetteAction> availableActions;

    private List<Card> cardsOnTable;

    public TressetteInfoSet(int playerIndex, Hand hand, List<Card> playedCards, List<Card> drawnCards,int numOfTurns,List<TressetteAction> availableActions,List<Card> cardsOnTable){
        this.playedCards=playedCards;
        this.playerIndex=playerIndex;
        this.hand=hand;
        this.drawnCards=drawnCards;
        this.numOfTurns=numOfTurns;
        this.availableActions=availableActions;
        this.cardsOnTable = cardsOnTable;
    }
    @Override
    public int getPlayerIndex() {
        return playerIndex;
    }

    @Override
    public TressetteGameState determinePseudoState() {
        return null;
    }

    @Override
    public List<TressetteGameState> getPossibleStates() {
        return null;
    }

    @Override
    public List<TressetteAction> getPlayerActions(int playerIndex) {

        return availableActions;
    }

    @Override
    public InformationSet<TressetteGameState, TressetteAction> getNextInformationSet(TressetteAction action) {
        return null;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TressetteInfoSet that)) return false;
        return playerIndex == that.playerIndex && numOfTurns == that.numOfTurns && Objects.equals(hand, that.hand) && Objects.equals(playedCards, that.playedCards) && Objects.equals(drawnCards, that.drawnCards) && Objects.equals(availableActions, that.availableActions) && Objects.equals(cardsOnTable, that.cardsOnTable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerIndex, hand, playedCards, drawnCards, numOfTurns, availableActions, cardsOnTable);
    }
}
