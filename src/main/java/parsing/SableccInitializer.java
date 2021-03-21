package parsing;

import justificationDiagram.JustificationDiagram;

public class SableccInitializer extends sablecc.syntax.analysis.DepthFirstAdapter implements Initializer {
    public JustificationDiagram diagram;

    @Override
    public JustificationDiagram create(String file) {

        return null;
    }

    @Override
    public void caseADiagram(ADiagram node) {
        super.caseADiagram(node);
    }

    @Override
    public void caseADeclarationInstruction(ADeclarationInstruction node) {
        super.caseADeclarationInstruction(node);
    }

    @Override
    public void caseARelationInstruction(ARelationInstruction node) {
        super.caseARelationInstruction(node);
    }

    @Override
    public void caseAElementDeclaration(AElementDeclaration node) {
        super.caseAElementDeclaration(node);
    }

    @Override
    public void caseAConclusionDeclaration(AConclusionDeclaration node) {
        super.caseAConclusionDeclaration(node);
    }
}
