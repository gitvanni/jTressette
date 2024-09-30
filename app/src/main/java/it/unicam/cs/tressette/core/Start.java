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

import org.example.App;

public class Start {
    public static void main(String[] args) {
        System.out.println("benvenuti al tressette");
        TressetteGame game = new TressetteGame();

        TressetteGameState current = game.getCurrentState();
        int currentPlayer = 0;
        do{
            currentPlayer = current.getCurrentPlayer();
            System.out.println("E' il turno del giocatore"+currentPlayer);
            TressetteAction action = game.getPlayers().get(currentPlayer).getAction(current);
            current = (TressetteGameState) current.applyAction(action);
        }while(!current.isTerminalNode());
        System.out.println("La partita è finita");
        System.out.println("Il giocatore 0 ha fatto punti: "+current.getPlayerScore(0));
        System.out.println("Il giocatore 1 ha fatto punti: "+current.getPlayerScore(1));
        System.out.println("Il giocatore 0 ha utility: "+current.getUtility(0));
        System.out.println("Il giocatore 1 ha utility: "+current.getUtility(1));
    }
}

