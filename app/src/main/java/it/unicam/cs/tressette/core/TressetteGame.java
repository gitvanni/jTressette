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

import com.lostrucos.jabtbg.core.Game;
import com.lostrucos.jabtbg.core.InformationSet;

import java.util.ArrayList;
import java.util.List;

public class TressetteGame implements Game<TressetteGameState,TressetteAction> {
    private final TressetteGameState currentState;
    private final TressetteGameState previousState;
    private int currentPlayer=0;
    private final List<TressettePlayer> players;
    private int player1Score=0;
    private int player2Score=0;



    public TressetteGame(){
        players = new ArrayList<>();
        initializePlayers();
        currentState = new TressetteGameState(currentPlayer,players.getFirst().getHand(), players.getLast().getHand());
        handleAccusi();
        previousState = currentState;
    }

    private void initializePlayers() {
        TressettePlayer player0 = new HumanPlayer(0);
        TressettePlayer player1 = new IAPlayer(1,this,1);
        //TressettePlayer player1 = new HumanPlayer(1);
        this.players.add(player0);
        this.players.add(player1);
    }

    /**
     * Gestisce la gestione degli accusi nel primo turno
     */
    private void handleAccusi() {
        int firstPlayerAccusiPoints = this.currentState.getAccusiPoint(0);
        int secondPlayerAccusiPoints = this.currentState.getAccusiPoint(1);
        player1Score+=firstPlayerAccusiPoints;
        player2Score+=secondPlayerAccusiPoints;
        System.out.println("Accusi points of Player 0: "+player1Score);
        System.out.println("Accusi points of Player 1: "+player2Score);
    }

    @Override
    public TressetteGameState getNextState(TressetteGameState state, TressetteAction action) {
        return (TressetteGameState) state.applyAction(action);
    }

    @Override
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public InformationSet<TressetteGameState, TressetteAction> getInformationSet(int playerIndex, TressetteGameState gameState) {
        TressetteGameState state = (TressetteGameState) gameState.deepCopy();
        List<Card> playedCards = state.getTable().getPlayerTakes(0);
        playedCards.addAll(state.getTable().getPlayerTakes(1));
        return new TressetteInfoSet(playerIndex,state.getPlayerHands().get(playerIndex),playedCards,state.getDrawnCards(),state.getTurn(),state.getAvailableActions(playerIndex),state.getTable().getCardsOnTheTable());
    }

    public TressetteGameState getCurrentState(){
        return currentState;
    }

    public List<TressettePlayer> getPlayers() {
        return players;
    }







    //TODO: sono i nuovi metodi
    @Override
    public List<TressetteAction> getAvailableActions(int playerIndex, TressetteGameState state) {
        return null;
    }

    @Override
    public TressetteGameState deepCopy(TressetteGameState state) {
        return (TressetteGameState) state.deepCopy();
    }
}
