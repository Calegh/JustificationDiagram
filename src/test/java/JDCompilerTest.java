import org.junit.jupiter.api.Test;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import com.ginsberg.junit.exit.ExpectSystemExitWithStatus;
import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;
import java.io.PrintStream;

class JDCompilerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void emptyDiagram() {
        try {
            JDCompiler.main(new String[] {"src/test/resources/emptyDiagram.jd"});
        } catch (IOException ignored) { }
    }

    @Test
    void conclusionOnly() {
        try {
            JDCompiler.main(new String[] {"src/test/resources/conclusionOnly.jd"});
        } catch (IOException ignored) { }
    }

    @Test
    void strategyOnly() {
        try {
            JDCompiler.main(new String[] {"src/test/resources/strategyOnly.jd"});
        } catch (IOException ignored) { }
    }

    @Test
    void supportOnly() {
        try {
            JDCompiler.main(new String[] {"src/test/resources/supportOnly.jd"});
        } catch (IOException ignored) { }
    }

    @Test
    void strategyToConclusion() {
        try {
            JDCompiler.main(new String[] {"src/test/resources/strategyToConclusion.jd"});
        } catch (IOException ignored) { }
    }

    @Test
    @ExpectSystemExitWithStatus(1)
    void emptyLabel() throws IOException {
        JDCompiler.main(new String[]{"src/test/resources/emptyLabel.jd"});
    }

    @Test
    @ExpectSystemExitWithStatus(1)
    void emptyRestriction() throws IOException {
        JDCompiler.main(new String[]{"src/test/resources/emptyRestriction.jd"});
    }

    @Test
    @ExpectSystemExitWithStatus(1)
    void doubleConclusion() throws IOException {
        JDCompiler.main(new String[]{"src/test/resources/doubleConclusion.jd"});
    }

    @Test
    @ExpectSystemExitWithStatus(1)
    void nonFinalConclusion() throws IOException {
        JDCompiler.main(new String[]{"src/test/resources/nonFinalConclusion.jd"});
        //assertFalse(errContent.toString().contains("Semantic error"));
    }

    /*
    @Test
    public void executeSomeCodeAFTERsystemExit() throws IOException {
        //exit.expectSystemExitWithStatus(1);
        exit.expectSystemExit();
        exit.checkAssertionAfterwards(new Assertion() {
            @Override
            public void checkAssertion() throws Exception {
                assertTrue(errContent.toString().contains("Semantic error"));
                assertTrue(errContent.toString().contains("the conclusion can't have any outgoing link. Use subconclusion instead"));
            }
        });
        JDCompiler.main(new String[]{"src/test/resources/nonFinalConclusion.jd"});
        System.exit(0);
    } */

    @Test
    @ExpectSystemExitWithStatus(1)
    void undeclaredElementSource() throws IOException {
        JDCompiler.main(new String[]{"src/test/resources/undeclaredElementSource.jd"});
    }

    @Test
    @ExpectSystemExitWithStatus(1)
    void undeclaredElementDestination() throws IOException {
        JDCompiler.main(new String[]{"src/test/resources/undeclaredElementDestination.jd"});
    }
}