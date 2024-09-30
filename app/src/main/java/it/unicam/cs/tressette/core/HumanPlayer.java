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

import com.lostrucos.jabtbg.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * This class represents a human player in tressette
 */
public class HumanPlayer extends TressettePlayer implements Player<TressetteGameState,TressetteAction>{

    public HumanPlayer(int id, Hand hand) {
        super(id,hand);
    }

    public HumanPlayer(int id){ super(id);}

    @Override
    public TressetteAction getAction(TressetteGameState state) {

        List<TressetteAction> availableActions = state.getAvailableActions(this.getPlayerIndex());
        for(int i =0; i<availableActions.size();i++){
            System.out.println(i+":"+availableActions.get(i).getCard().toString());
        }
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        boolean validChoice = false;

        // Loop until the player makes a valid choice
        while (!validChoice) {
            System.out.println("Enter the number corresponding to your choice: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character left over
                // Validate if the choice is within the range of available actions
                if (choice >= 0 && choice < availableActions.size()) {
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice. Please choose a number between 0 and " + (availableActions.size() - 1));
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
            }


        }

        // Return the selected action
        TressetteAction result = availableActions.get(choice);
        //this.getHand().getCards().remove(result.getCard());
        return availableActions.get(choice);
    }


}
