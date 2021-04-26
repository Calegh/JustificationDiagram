package parsing.sablecc.semanticAnalysis;

public class ConclusionInfo extends ElementInfo {
    String restriction;

    public ConclusionInfo(String name, String label, String restriction) {
        super(name, "conclusion", label);
        this.restriction = restriction;
    }
}
