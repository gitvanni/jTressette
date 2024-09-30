package com.lostrucos.jabtbg.core;

import java.util.List;

/**
 * Represents a game of both a perfect-information and an imperfect-but-complete-information game.
 */
public interface Game<T extends GameState<E>, E extends Action> {

    /**
     * Updates the given game state by applying an action
     *
     * @param state the state to be updated
     * @param action the action to be applied
     * @return the new state after having applied the action
     */
    T getNextState(T state, E action);

    /**
     * Method that returns the index of the current player
     *
     * @return the index of the current player
     */
    int getCurrentPlayer();



    /**
     * Returns the information set for a given player in a given game state.
     *
     * @param playerIndex the index of the player.
     * @param gameState   the current state of the game.
     * @return the information set for the player.
     */
    InformationSet<T, E> getInformationSet(int playerIndex, T gameState);
//TODO:metodi nuovi qui
    /**
     * Returns the list of available actions for the given player at the given state
     *
     * @param playerIndex the index of the player
     * @param state the given state of the game
     * @return the list of available actions for the given player at the given state
     */
    List<E> getAvailableActions(int playerIndex, T state);

    /**
     * Returns the deep copy of the given state
     *
     * @param state the state of the game
     * @return Returns the deep copy of the given state
     */
    T deepCopy(T state);


}


