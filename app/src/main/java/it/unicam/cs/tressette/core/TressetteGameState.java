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

import com.lostrucos.jabtbg.core.GameState;
import com.lostrucos.jabtbg.core.InformationSet;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class represents
 */
public class TressetteGameState implements GameState<TressetteAction> {
    private final Deck deck;
    private int currentPlayer;
    private final TressetteTable table;
    private int turn;

    private final Map<Integer,Hand> playerHands;

    private final List<Card> drawnCards;

    private int lastTrickWinner;



    /**
     * Used to create the initial game state of the tressette game
     * @param currentPlayer the index of the current player
     */
    public TressetteGameState(int currentPlayer, Hand player0Hand, Hand player1Hand) {
        this.deck = new Deck();
        deck.shuffle();
        this.currentPlayer=currentPlayer;
        playerHands = new HashMap<>();
        this.playerHands.put(0,player0Hand);
        this.playerHands.put(1,player1Hand);
        this.table = new TressetteTable();
        this.drawnCards=new ArrayList<>();
        dealCards();
        turn = 0;
    }

    // Copy constructor for deep copying the TressetteGameState
    public TressetteGameState(TressetteGameState otherGameState) {
        this.deck = otherGameState.deck.deepCopy(); // Deep copy the deck
        this.currentPlayer = otherGameState.currentPlayer; // Copy the current player index
        this.table = otherGameState.table.deepCopy(); // Deep copy the table
        this.turn = otherGameState.turn; // Copy the turn number

        // Deep copy the playerHands map
        this.playerHands = new HashMap<>();
        for (Map.Entry<Integer, Hand> entry : otherGameState.playerHands.entrySet()) {
            this.playerHands.put(entry.getKey(), entry.getValue().deepCopy());
        }

        // Deep copy the drawnCards list
        this.drawnCards = deepCopyCardList(otherGameState.drawnCards);
        this.lastTrickWinner = otherGameState.lastTrickWinner;
    }

    // Deep copy method for the TressetteGameState class

    // Helper method to deep copy a list of cards
    private List<Card> deepCopyCardList(List<Card> cardList) {
        List<Card> copiedList = new ArrayList<>();
        for (Card card : cardList) {
            copiedList.add(card.deepCopy()); // Deep copy each card
        }
        return copiedList;
    }
    private void dealCards() {
        this.playerHands.get(0).addToHand(deck.deal(10));
        this.playerHands.get(1).addToHand(deck.deal(10));
    }


    /**
     * Creates a tressette game state with the given information
     *
     * @param deck          the deck of cards
     * @param currentPlayer the index of the current player
     * @param table the current elements on the table
     */
    public TressetteGameState(Deck deck, int currentPlayer,TressetteTable table,Map<Integer,Hand> playerHands,int turn, List<Card> drawnCards){
        this.deck = deck;
        this.currentPlayer = currentPlayer;
        this.table = table;
        this.turn = turn;
        this.playerHands = playerHands;
        this.drawnCards=drawnCards;

    }


    @Override
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public boolean isTerminalNode() {
        return (deck.isEmpty() && table.getPlayerTakes(0).size()+table.getPlayerTakes(1).size() == 40);
    }

    @Override
    public GameState<TressetteAction> applyAction(TressetteAction action) {
        this.playerHands.get(currentPlayer).getCards().remove(action.getCard());
        if(table.isEmpty()){
            return firstTurn(action);
        }
        return secondTurn(action);
    }

    @Override
    public double getUtility(int playerIndex) {
        if(!isTerminalNode())
            throw new IllegalStateException("State is not final!");
        int playerScore = getPlayerScore(playerIndex);
        return switch (playerScore) {
            case 0 -> -6; // Opponent scores 11
            case 1 -> -5; // Opponent scores 10
            case 2 -> -4; // Opponent scores 9
            case 3 -> -3; // Opponent scores 8
            case 4 -> -2; // Opponent scores 7
            case 5 -> -1; // Opponent scores 6
            case 6 -> 1;  // Opponent scores 5
            case 7 -> 2;  // Opponent scores 4
            case 8 -> 3;  // Opponent scores 3
            case 9 -> 4;  // Opponent scores 2
            case 10 -> 5; // Opponent scores 1
            case 11 -> 6; // Opponent scores 0
            default -> 0; // Fallback (should never happen)
        };
    }


    @Override
    public InformationSet<GameState<TressetteAction>, TressetteAction> getInfoSet(int playerIndex) {
        return null;
    }

    @Override
    public GameState<TressetteAction> deepCopy() {
        return new TressetteGameState(this);
    }

    @Override
    public List<TressetteAction> getAvailableActions(int playerIndex) {
        List<TressetteAction> result;
        Hand hand = playerHands.get(playerIndex);
        if(table.isEmpty()){
            return getAllActions(playerIndex,hand);
        }
        else{
            Card cardOnTable = table.getCardsOnTheTable().getFirst();
            result = getActionsBasedOnSuit(playerIndex,hand,cardOnTable);
            if(result.isEmpty()){
                return getAllActions(playerIndex,hand);
            }
        }
        return result;
    }

    private GameState<TressetteAction> firstTurn(TressetteAction action){
        table.addCardOnTheTable(action.getCard());
        System.out.println(table);
        return new TressetteGameState(this.deck,1-currentPlayer,this.table,this.playerHands,this.turn,this.drawnCards);
    }

    private GameState<TressetteAction> secondTurn(TressetteAction action){
        int winner = -1;
        table.addCardOnTheTable(action.getCard());
        System.out.println(table);
        winner = winnerOfTheTrick(1-currentPlayer,table.getCardsOnTheTable().getFirst(),currentPlayer,action.getCard());
        if(turn==19){
            lastTrickWinner=winner;
        }
        table.addCardsToTakes(winner,table.getCardsOnTheTable());
        table.clearTable();
        if(!deck.isEmpty())
            drawCards(winner);
        return new TressetteGameState(this.deck,winner,this.table,this.playerHands,this.turn+1,this.drawnCards);
    }

    private void drawCards(int winner) {
        Card first = deck.deal();
        Card second = deck.deal();
        drawnCards.add(first);
        drawnCards.add(second);
        playerHands.get(winner).addToHand(first);
        playerHands.get(1-winner).addToHand(second);
    }

    private int winnerOfTheTrick(int player1,Card card1,int player2, Card card2){
        if(card1.isOfSameSuit(card2)){
            int result = card1.compareTo(card2);
            if(result>0)
                return player1;
            else return player2;
        }
        return player1;
    }



    //La lista di azioni è la lista di tutte le carte in mano
    private List<TressetteAction> getAllActions(int playerIndex, Hand hand){
        return hand.getCards().stream().
                map(card->new TressetteAction(playerIndex,card)).
                collect(Collectors.toList());
    }

    //La lista di azioni è la lista di tutte le carte in mano dello stesso seme della carta sul tavolo
    private List<TressetteAction> getActionsBasedOnSuit(int playerIndex, Hand hand, Card cardOnTable){
        return  hand.getCards().stream().
                filter(card->card.isOfSameSuit(cardOnTable)).
                map(card-> new TressetteAction(playerIndex,card)).
                collect(Collectors.toList());
    }

    public int getPlayerScore(int playerIndex){
        List<Card> takes = table.getPlayerTakes(playerIndex);
        int numOfFigures = (int) takes.stream().
                           filter(Card::isFigureOrThreeOrTwo).
                           count();
        int numOfAces = (int) takes.stream().filter(card -> card.getRank() == Rank.ACE).count();
        return playerIndex==lastTrickWinner ? (numOfFigures/3)+numOfAces+1 : (numOfFigures/3)+numOfAces ;
    }

    public int getAccusiPoint(int playerIndex) {
        List<Card> hand = playerHands.get(playerIndex).getCards();
        int napoliPoints = getNapoliPoints(hand);
        int bongiocoPoints = getBongiocoPoints(hand);
        return napoliPoints+bongiocoPoints;
    }

    private int getNapoliPoints(List<Card> hand){
        Map<Suit, Set<Rank>> suitToRanks = hand.stream()
                .filter(card -> card.getRank() == Rank.ACE || card.getRank() == Rank.TWO || card.getRank() == Rank.THREE)
                .collect(Collectors.groupingBy(Card::getSuit,
                        Collectors.mapping(Card::getRank, Collectors.toSet())));
        int points = 0;
        // 4. Add 3 points for each suit that contains Ace, Two, and Three
        points += (int) (suitToRanks.values().stream()
                        .filter(ranks -> ranks.containsAll(Set.of(Rank.ACE, Rank.TWO, Rank.THREE)))
                        .count() * 3);

        return points;

    }

    private int getBongiocoPoints(List<Card> hand){
        Map<Rank, Long> rankCount = hand.stream()
                .filter(card -> card.getRank() == Rank.TWO || card.getRank() == Rank.THREE || card.getRank() == Rank.ACE)
                .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));

        return rankCount.values().stream()
                .mapToInt(count -> {
                    if (count == 3) {
                        return 3;
                    } else if (count == 4) {
                        return 4;
                    } else {
                        return 0;
                    }
                })
                .sum();
    }

    public Map<Integer, Hand> getPlayerHands() {
        return playerHands;
    }

    public TressetteTable getTable() {
        return table;
    }

    public List<Card> getDrawnCards() {
        return drawnCards;
    }

    public int getTurn() {
        return turn;
    }









    //TODO: metodo inutile nel tressette
    @Override
    public boolean isTie() {
        return false;
    }


    //TODO: inutile nel tressette
    @Override
    public List<Integer> getPlayersInGame() {
        return null;
    }

    //TODO: inutile nel tressette
    @Override
    public boolean isPlayerStillInGame(int player) {
        return false;
    }
}
