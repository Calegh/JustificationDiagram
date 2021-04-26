package parsing.sablecc.semanticAnalysis;

import sablecc.syntax.node.Token;

public class SemanticException extends RuntimeException {

    public SemanticException(Token token, String message) {
        super("[" + token.getLine() + ":" + token.getPos() + "] "
                + message);
    }
}
