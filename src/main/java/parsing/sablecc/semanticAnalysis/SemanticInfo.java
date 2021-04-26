package parsing.sablecc.semanticAnalysis;

import java.util.HashMap;
import java.util.Map;

public class SemanticInfo {
    Map<String, ElementInfo> elements = new HashMap<>();
    ConclusionInfo conclusion;

    public boolean declaredNode(String name) {
        return elements.containsKey(name);
    }

    public void addElement(String name, String type, String label) {
        elements.put(name, new ElementInfo(name, type, label));
    }

    public void addConclusion(String name, String label, String restriction) {
        conclusion = new ConclusionInfo(name, label, restriction);
        elements.put(name, conclusion);
    }

    public ElementInfo getElement(String name) {
        return elements.get(name);
    }
}
