package zxh.demo.args.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import zxh.demo.args.internal.exception.AnalyzeException;

import java.util.Map;

import static org.junit.Assert.*;

public class AnalyzerTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validate_boolean() {
        String input = "-b";
        Map<String, String> result = Analyzer.analyze(input);
        assertEquals("", result.get("b"));
    }

    @Test
    public void validate_integer() {
        String input = "-i 8080";
        Map<String, String> result = Analyzer.analyze(input);
        assertEquals("8080", result.get("i"));
    }

    @Test
    public void validate_double() {
        String input = "-d -20.1";
        Map<String, String> result = Analyzer.analyze(input);
        assertEquals("-20.1", result.get("d"));
    }

    @Test
    public void validate_string() {
        String input = "-s iamstring";
        Map<String, String> result = Analyzer.analyze(input);
        assertEquals("iamstring", result.get("s"));
    }

    @Test
    public void validate_doubleArray() {
        String input = "-d -10.1,11,12.0";
        Map<String, String> result = Analyzer.analyze(input);
        assertEquals("-10.1,11,12.0", result.get("d"));
    }

    @Test
    public void validate_allType() {
        String input = "-b -i 8080 -s iamstring -d -10.1,11,12.0";
        Map<String, String> result = Analyzer.analyze(input);
        assertEquals("", result.get("b"));
        assertEquals("8080", result.get("i"));
        assertEquals("iamstring", result.get("s"));
        assertEquals("-10.1,11,12.0", result.get("d"));
    }

    @Test
    public void validate_wrongFlagFormat() {
        String input = "-i 8080 d -20.1";

        expectedException.expect(AnalyzeException.class);
        expectedException.expectMessage("Invalid integer type flag i of value: 8080 d -20.1");
        Analyzer.analyze(input);
    }
}