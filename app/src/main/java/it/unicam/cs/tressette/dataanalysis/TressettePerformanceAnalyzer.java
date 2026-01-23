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

import com.lostrucos.jabtbg.dataanalysis.PerformanceAnalyzer;
import it.unicam.cs.tressette.core.TressetteAction;
import it.unicam.cs.tressette.core.TressetteGameState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TressettePerformanceAnalyzer implements PerformanceAnalyzer<TressetteGameState, TressetteAction> {
    @Override
    public Map<String, Object> compareAlgorithms(List<Map<String, Object>> data) {
        Map<String, Object> analysis = new HashMap<>();
        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> algorithmData = data.get(i);
            List<Map<String, Object>> turns = (List<Map<String, Object>>) algorithmData.get("turns");

            long totalDecisionTime = turns.stream()
                    .mapToLong(turn -> (Long) turn.get("decisionTime"))
                    .sum();
            double avgDecisionTime = totalDecisionTime / (double) turns.size();

            Map<String, Object> algorithmAnalysis = new HashMap<>();
            algorithmAnalysis.put("totalDecisionTime", totalDecisionTime);
            algorithmAnalysis.put("averageDecisionTime", avgDecisionTime);
            algorithmAnalysis.put("numberOfMoves", turns.size());

            analysis.put("Algorithm " + (i + 1), algorithmAnalysis);
        }
        return analysis;
    }
}
