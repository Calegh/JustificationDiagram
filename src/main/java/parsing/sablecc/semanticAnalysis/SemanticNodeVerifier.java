package parsing.sablecc.semanticAnalysis;

import sablecc.syntax.analysis.DepthFirstAdapter;
import sablecc.syntax.node.*;

public class SemanticNodeVerifier extends DepthFirstAdapter {
    SemanticInfo semantics;

    public SemanticNodeVerifier(SemanticInfo semantics) {
        this.semantics = semantics;
    }

    @Override
    public void caseAConclusionWithoutRestrictionConclusion(AConclusionWithoutRestrictionConclusion node) {
        if (semantics.conclusion != null) {
            throw new SemanticException(node.getAlias(),
                    "the diagram can only contain one conclusion");
        }

        if (semantics.declaredNode(node.getAlias().getText())) {
            throw new SemanticException(node.getAlias(),
                    "duplicate element declaration found");
        }

        if (node.getLabel().getText().equals("\"\"")) {
            throw new SemanticException(node.getLabel(),
                    "empty label found");
        }
        semantics.addConclusion(node.getAlias().getText(), node.getLabel().getText(), null);
        super.caseAConclusionWithoutRestrictionConclusion(node);
    }

    @Override
    public void caseAConclusionWithRestrictionConclusion(AConclusionWithRestrictionConclusion node) {
        if (semantics.conclusion != null) {
            throw new SemanticException(node.getAlias(),
                    "the diagram can only contain one conclusion");
        }

        if (semantics.declaredNode(node.getAlias().getText())) {
            throw new SemanticException(node.getAlias(),
                    "duplicate element declaration found");
        }

        if (node.getLabel().getText().equals("\"\"")) {
            throw new SemanticException(node.getLabel(),
                    "empty label found");
        }

        if (node.getRestriction().getText().equals("\"\"")) {
            throw new SemanticException(node.getLabel(),
                    "empty restriction found");
        }
        semantics.addConclusion(node.getAlias().getText(),
                node.getLabel().getText(),
                node.getRestriction().getText());
        super.caseAConclusionWithRestrictionConclusion(node);
    }

    @Override
    public void caseAElement(AElement node) {
        if (semantics.declaredNode(node.getAlias().getText())) {
            throw new SemanticException(node.getAlias(),
                    "duplicate element declaration found");
        }

        if (node.getLabel().getText().equals("\"\"")) {
            throw new SemanticException(node.getLabel(),
                    "empty label found");
        }
        semantics.addElement(node.getAlias().getText(), node.getType().getText(), node.getLabel().getText());
        super.caseAElement(node);
    }
}
