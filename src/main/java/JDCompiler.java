import export.GraphDrawer;
import export.RequirementsLister;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import justificationDiagram.JustificationDiagram;
import org.apache.commons.cli.*;
import parsing.*;
import parsing.antlr.AntlrInitializer;
import parsing.antlr.AntlrLinker;
import parsing.sablecc.SableccInitializer;
import parsing.sablecc.SableccLinker;

import java.io.*;

public class JDCompiler {

    public static void main(String[] args) throws IOException {
        CommandLine cmd = setup(args);
        String outputOption = cmd.getOptionValue("output");

        if (outputOption != null) {
            if (!outputIsValid(outputOption)) {
                System.exit(1);
            }
        }
        for (int i = 0; i < cmd.getArgs().length; ++i) {
            String inputFile = cmd.getArgs()[i];
            if (!inputIsValid(inputFile)) {
                continue;
            }
            generateFiles(cmd, cmd.getArgs()[i], generateOutputName(outputOption, i, inputFile));
        }
    }

    private static CommandLine setup(String[] args) {
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        Option output = new Option("o", "output", true, "output file path");
        options.addOption(output);

        options.addOption("png", "generate graph");
        options.addOption("gv", "generate gv file");
        options.addOption("td", "generate todo list");

        try {
            return parser.parse(options, args);
        } catch (org.apache.commons.cli.ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return null;
        }
    }

    private static void generateFiles(CommandLine cmd, String inputFilePath, String outputFilePath) throws IOException {
        JustificationDiagram diagram = createDiagram(inputFilePath);
        GraphDrawer drawer = new GraphDrawer();
        StringBuilder gv = drawer.draw(diagram);

        if (cmd.hasOption("png")) {
            InputStream dot = new ByteArrayInputStream(gv.toString().getBytes());
            MutableGraph g = new guru.nidi.graphviz.parse.Parser().read(dot);
            Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(outputFilePath + ".png"));
        }
        if (cmd.hasOption("gv")) {
            PrintWriter out = new PrintWriter(new FileWriter(outputFilePath + ".gv"));
            out.print(gv);
            out.close();
        }
        if (cmd.hasOption("td")) {
            PrintWriter out = new PrintWriter(new FileWriter(outputFilePath + ".todo"));
            RequirementsLister lister = new RequirementsLister();
            out.print(lister.generate(diagram));
            out.close();
        }
    }

    private static boolean inputIsValid(String in) {
        if (!in.matches(".*\\.(jd|txt|jdevops)")) {
            System.err.println(in + " is not a valid name. The input file should end with .jd or .txt");
            return false;
        }
        return true;
    }

    private static String generateOutputName(String outputOption, int index, String inputOption) {
        if (outputOption != null) {
            if (index > 0) {
                return outputOption + index;
            } else {
                return outputOption;
            }
        } else {
            return inputOption.split("\\.")[0];
        }
    }

    private static boolean outputIsValid(String out) {
        if (out.matches(".*\\..*")) {
            System.err.println("The output file should not have an extension");
            return false;
        }
        return true;
    }

    public static JustificationDiagram createDiagram(String file) {
        Initializer factory = new SableccInitializer();
        JustificationDiagram diagram = factory.create(file);
        Linker linker = new SableccLinker(diagram);
        return linker.link((file));
    }

/*    public static JustificationDiagram createDiagram(String file) {
        Initializer factory = new AntlrInitializer();
        JustificationDiagram diagram = factory.create(file);
        Linker linker = new AntlrLinker(diagram);
        return linker.link((file));
    }*/

}
