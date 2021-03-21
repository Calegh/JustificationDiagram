package parsing.sablecc;

import justificationDiagram.JustificationDiagram;
import models.Conclusion;
import models.NodeFactory;
import parsing.Initializer;
import sablecc.syntax.analysis.*;
import sablecc.syntax.node.*;

public class SableccInitializer extends DepthFirstAdapter implements Initializer {
    public JustificationDiagram diagram;

    @Override
    public JustificationDiagram create(String file) {
        Start tree = SableccParser.parse(file);
        tree.apply(this);
        return diagram;
    }

    @Override
    public void caseADiagram(ADiagram node) {
        diagram = new JustificationDiagram();
        super.caseADiagram(node);
    }

    @Override
    public void caseAElement(AElement node) {
        diagram.nodes.put(node.getAlias().getText(),
                NodeFactory.create(node.getType().getText(),
                                    node.getAlias().getText(),
                                    node.getLabel().getText()));
        super.caseAElement(node);
    }

    @Override
    public void caseAConclusionWithoutRestrictionConclusion(AConclusionWithoutRestrictionConclusion node) {
        diagram.nodes.put(node.getAlias().getText(),
                new Conclusion(node.getAlias().getText(), node.getLabel().getText(), null));
        super.caseAConclusionWithoutRestrictionConclusion(node);
    }

    @Override
    public void caseAConclusionWithRestrictionConclusion(AConclusionWithRestrictionConclusion node) {
        diagram.nodes.put(node.getAlias().getText(),
                new Conclusion(node.getAlias().getText(), node.getLabel().getText(), node.getRestriction().getText()));
        super.caseAConclusionWithRestrictionConclusion(node);
    }
}
