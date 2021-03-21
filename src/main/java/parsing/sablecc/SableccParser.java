package parsing.sablecc;

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
            return parser.parse();

        } catch (FileNotFoundException e) {
            System.err.println(file + " : file not found.");
            System.exit(1);
        } catch (ParserException e) {
            System.err.println("Syntax error : " + e.getMessage());
            System.exit(1);
        } catch (LexerException e) {
            System.err.println("Lexical error : " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("IO error : " + e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
