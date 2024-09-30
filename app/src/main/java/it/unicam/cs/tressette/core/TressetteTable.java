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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the table of the tressette game.
 * The table is formed by the cards, currently played by the players,
 * and by the piles of cards that are the takes of each player so far.
 */
public class TressetteTable {
    private List<Card> cardsOnTheTable;
    private final Map<Integer,List<Card>> takes;

    /**
     * The default construction of the table, which happens at the start of the game,
     * has no cards in it.
     */
    public TressetteTable(){
        this.takes = new HashMap<>();
        takes.put(0,new ArrayList<>());
        takes.put(1,new ArrayList<>());
        this.cardsOnTheTable=new ArrayList<>();
    }
    public TressetteTable(List<Card> cardsOnTheTable, Map<Integer, List<Card>> takes) {
        this.cardsOnTheTable = cardsOnTheTable;
        this.takes = takes;
    }

    public TressetteTable(TressetteTable otherTable) {
        // Deep copy cards on the table
        this.cardsOnTheTable = new ArrayList<>();
        for (Card card : otherTable.cardsOnTheTable) {
            this.cardsOnTheTable.add(card.deepCopy());
        }

        // Deep copy the takes map
        this.takes = new HashMap<>();
        for (Map.Entry<Integer, List<Card>> entry : otherTable.takes.entrySet()) {
            // Deep copy the list of cards for each player
            this.takes.put(entry.getKey(), deepCopyCardList(entry.getValue()));
        }
    }

    // Deep copy method for the TressetteTable class
    public TressetteTable deepCopy() {
        return new TressetteTable(this); // Use the copy constructor
    }

    // Helper method to deep copy a list of cards
    private List<Card> deepCopyCardList(List<Card> cardList) {
        List<Card> copiedList = new ArrayList<>();
        for (Card card : cardList) {
            copiedList.add(card.deepCopy()); // Deep copy each card
        }
        return copiedList;
    }

    /**
     * Returns the current cards on the table
     *
     * @return the current cards on the table
     */
    public List<Card> getCardsOnTheTable(){
        return cardsOnTheTable;
    }

    /**
     * Returns the pile of takes for the specified player
     *
     * @param playerId the id of the player
     * @return the pile of takes for the specified player
     */
    public List<Card> getPlayerTakes(int playerId){
        return takes.get(playerId);
    }

    /**
     * This method tells if the table is empty, meaning there are no cards in it
     *
     * @return true, if the table is empty
     *         false otherwise
     */
    public boolean isEmpty(){
        return cardsOnTheTable.isEmpty();
    }

    public void addCardOnTheTable(Card card){
        this.cardsOnTheTable.add(card);
    }

    public void addCardsToTakes(int playerIndex, List<Card> take){
        List<Card> alreadyPresent = this.takes.get(playerIndex);
        alreadyPresent.addAll(take);
    }
//TODO: rimuovere le carte sul tavolo direttamente quando vengono fatte le prese
    public void clearTable(){
        this.cardsOnTheTable = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "TressetteTable{" +
                "cardsOnTheTable=" + cardsOnTheTable +
                '}';
    }
}
