package parsing.antlr;

import justificationDiagram.JustificationDiagram;
import models.Relation;
import models.RelationFactory;
import parsing.JustificationDiagramBaseVisitor;
import parsing.Linker;

public class AntlrLinker extends JustificationDiagramBaseVisitor<String> implements Linker {
    public JustificationDiagram diagram;

    public AntlrLinker(JustificationDiagram diagram) {
        this.diagram = diagram;
    }

    public JustificationDiagram link(String file) {
        visit(AntlrParser.parse((file)));
        return diagram;
    }

    @Override
    public String visitRelation(parsing.JustificationDiagramParser.RelationContext ctx) {
        Relation relation = RelationFactory.create(ctx.LINK().getText(), diagram.nodes.get(ctx.ALIAS(0).getText()),
                diagram.nodes.get(ctx.ALIAS(1).getText()));
        diagram.relations.add(relation);
        diagram.nodes.get(ctx.ALIAS(0).getText()).addOutput(relation);
        diagram.nodes.get(ctx.ALIAS(1).getText()).addInput(relation);
        return super.visitRelation(ctx);
    }
}
