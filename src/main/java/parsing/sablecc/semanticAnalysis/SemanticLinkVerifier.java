package parsing.sablecc.semanticAnalysis;

import sablecc.syntax.analysis.DepthFirstAdapter;
import sablecc.syntax.node.ARelation;

public class SemanticLinkVerifier extends DepthFirstAdapter {
    SemanticInfo semantics;

    public SemanticLinkVerifier(SemanticInfo semantics) {
        this.semantics = semantics;
    }

    @Override
    public void caseARelation(ARelation node) {
        // Pas de lien sur des nodes inexistante
        if (!semantics.declaredNode(node.getLeft().getText())) {
            throw new SemanticException(node.getLeft(), "link to undeclared element " + node.getLeft().getText());
        }

        if (!semantics.declaredNode(node.getRight().getText())) {
            throw new SemanticException(node.getRight(), "link to undeclared element " + node.getRight().getText());
        }

        // Règles des diagrammes de justification
        if (node.getLink().getText().equals("-->")) {
            ElementInfo source = semantics.getElement(node.getLeft().getText());
            ElementInfo destination = semantics.getElement(node.getRight().getText());

            switch (source.type) {
                case "conclusion":
                    throw new SemanticException(node.getRight(),
                            "the conclusion can't have any outgoing link. Use subconclusion instead");
                case "domain":
                    if (!destination.type.equals("strategy")) {
                        throw new SemanticException(node.getRight(), "a domain can only apply to a strategy");
                    }
                    break;
                case "rationale":
                    if (!destination.type.equals("strategy")) {
                        throw new SemanticException(node.getRight(), "a rationale can only apply to a strategy");
                    }
                    break;
                case "strategy":
                    if (!destination.type.equals("conclusion") && !destination.type.equals("subconclusion")) {
                        throw new SemanticException(node.getRight(),
                                "a strategy can only lead to a conclusion or a subconclusion");
                    }
                    break;
                case "subconclusion":
                    if (!destination.type.equals("strategy")) {
                        throw new SemanticException(node.getRight(), "a subconclusion can only lead to a strategy");
                    }
                    break;
                case "support":
                    if (!destination.type.equals("strategy")) {
                        throw new SemanticException(node.getRight(), "a support can only lead to a strategy");
                    }
                    break;
            }
        }
        super.caseARelation(node);
    }

    // TODO Feature : Graphe acyclique
}
