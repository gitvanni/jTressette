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

package it.unicam.cs.tressette.dataanalysis;

import com.lostrucos.jabtbg.dataanalysis.DataCollector;
import it.unicam.cs.tressette.core.TressetteAction;
import it.unicam.cs.tressette.core.TressetteGame;
import it.unicam.cs.tressette.core.TressetteGameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TressetteDataCollector implements DataCollector<TressetteGameState, TressetteAction> {
    private List<Map<String, Object>> turns = new ArrayList<>();
    private List<Map<String, Object>> score = new ArrayList<>();
    @Override
    public void collectData(TressetteGameState state, TressetteAction action, int player, long decisionTime) {
        Map<String, Object> turnData = new HashMap<>();
        turnData.put("state", state.toString());
        turnData.put("action", action.toString());
        turnData.put("player", player);
        turnData.put("decisionTime", decisionTime);
        turns.add(turnData);
    }

    public void collectScore(TressetteGame game){
        Map<String, Object> scoreData = new HashMap<>();
        scoreData.put("Game",game);
    }

    @Override
    public Map<String, Object> getCollectedData() {
        Map<String, Object> data = new HashMap<>();
        data.put("turns", turns);
        return data;
    }
}
