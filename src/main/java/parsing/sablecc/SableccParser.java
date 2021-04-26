package parsing.sablecc;

import parsing.sablecc.semanticAnalysis.SemanticException;
import parsing.sablecc.semanticAnalysis.SemanticInfo;
import parsing.sablecc.semanticAnalysis.SemanticLinkVerifier;
import parsing.sablecc.semanticAnalysis.SemanticNodeVerifier;
import sablecc.syntax.lexer.*;
import sablecc.syntax.node.Start;
import sablecc.syntax.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

public class SableccParser {
    public static Start parse(String file) {
        try {
            Lexer lexer = new Lexer(new PushbackReader(new FileReader(file)));
            Parser parser = new Parser(lexer);
            Start tree = parser.parse();

            // Vérification sémantique
            SemanticInfo semantics = new SemanticInfo();
            tree.apply(new SemanticNodeVerifier(semantics));
            tree.apply(new SemanticLinkVerifier(semantics));
            return tree;
        } catch (FileNotFoundException e) {
            System.err.println("File " + file + " not found.");
            System.exit(1);
        } catch (ParserException e) {
            System.err.println("Syntax error: " + e.getMessage());
            System.exit(1);
        } catch (LexerException e) {
            System.err.println("Lexer error: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
            System.exit(1);
        } catch (SemanticException e) {
            System.err.println("Semantic error: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
