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

import com.lostrucos.jabtbg.algorithms.crm.CFRMAlgorithm;
import com.lostrucos.jabtbg.core.Game;
import com.lostrucos.jabtbg.core.Player;

public class IAPlayer extends TressettePlayer implements Player<TressetteGameState,TressetteAction> {
    private int playerIndex;
    private CFRMAlgorithm<TressetteGameState, TressetteAction> cfrAlgorithm;
    private Game<TressetteGameState, TressetteAction> game;

    public IAPlayer(int id,Game<TressetteGameState,TressetteAction> game, int iterations){
        super(id);
        this.game = game;
        this.cfrAlgorithm = new CFRMAlgorithm<>(iterations, 1.0,this.game);
    }
    @Override
    public TressetteAction getAction(TressetteGameState state) {
        cfrAlgorithm.updateAfterAction(state, null);

        // Usa l'algoritmo CFR per scegliere l'azione migliore
        return cfrAlgorithm.chooseAction(state);
    }

    public void train(TressetteGameState initialState) {
        // Esegui il training dell'algoritmo CFR prima di iniziare a giocare
        cfrAlgorithm.initialize(initialState);
        // Il training effettivo avviene internamente in updateAfterAction
    }
}
