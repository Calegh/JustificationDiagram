package parsing.antlr;

import justificationDiagram.JustificationDiagram;
import models.Conclusion;
import models.NodeFactory;
import parsing.Initializer;
import parsing.JustificationDiagramBaseVisitor;
import parsing.JustificationDiagramParser;

public class AntlrInitializer extends JustificationDiagramBaseVisitor<String> implements Initializer {
    public JustificationDiagram diagram;

    @Override
    public JustificationDiagram create(String file) {
        visit(AntlrParser.parse(file));
        return diagram;
    }

    @Override
    public String visitDiagram(JustificationDiagramParser.DiagramContext ctx) {
        diagram = new JustificationDiagram();
        return super.visitDiagram(ctx);
    }

    @Override
    public String visitElement(JustificationDiagramParser.ElementContext ctx) {
        diagram.nodes.put(ctx.ALIAS().getText(),
                NodeFactory.create(ctx.TYPE().getText(), ctx.ALIAS().getText(),  ctx.label.getText()));
        return super.visitElement(ctx);
    }

    @Override
    public String visitConclusion(JustificationDiagramParser.ConclusionContext ctx) {
        diagram.nodes.put(ctx.ALIAS().getText(), new Conclusion(ctx.ALIAS().getText(),
                ctx.label.getText(), ctx.restriction != null? ctx.restriction.getText() : null));
        return super.visitConclusion(ctx);
    }
}
