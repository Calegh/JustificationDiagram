package parsing.sablecc;

import justificationDiagram.JustificationDiagram;
import models.Relation;
import models.RelationFactory;
import parsing.Linker;
import sablecc.syntax.analysis.DepthFirstAdapter;
import sablecc.syntax.node.ARelation;
import sablecc.syntax.node.Start;

public class SableccLinker extends DepthFirstAdapter implements Linker {
    public JustificationDiagram diagram;

    public SableccLinker(JustificationDiagram diagram) {
        this.diagram = diagram;
    }

    public JustificationDiagram link(String file) {
        Start tree = SableccParser.parse(file);
        tree.apply(this);
        return diagram;
    }

    @Override
    public void caseARelation(ARelation node) {
        Relation relation = RelationFactory.create(node.getLink().getText(), diagram.nodes.get(node.getLeft().getText()),
                diagram.nodes.get(node.getRight().getText()));
        diagram.relations.add(relation);
        diagram.nodes.get(node.getLeft().getText()).addOutput(relation);
        diagram.nodes.get(node.getRight().getText()).addInput(relation);
        super.caseARelation(node);
    }
}
