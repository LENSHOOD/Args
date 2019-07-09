package zxh.demo.args.internal;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class AnalyzerTest {

    @Test
    public void validate_single_boolean() {
        String inputArgs = "-l";
        Map<String, String> argsMap = Analyzer.analyze(inputArgs);
        assertEquals("", argsMap.get("l"));
    }

    @Test
    public void validate_single_number() {
        String inputArgs = "-d 20.1";
        Map<String, String> argsMap = Analyzer.analyze(inputArgs);
        assertEquals("20.1", argsMap.get("d"));
    }

    @Test
    public void validate_single_string_array() {
        String inputArgs = "-s a1,a2,a3";
        Map<String, String> argsMap = Analyzer.analyze(inputArgs);
        assertEquals("a1,a2,a3", argsMap.get("s"));
    }

    @Test
    public void validate_minus_number() {
        String inputArgs = "-m -10";
        Map<String, String> argsMap = Analyzer.analyze(inputArgs);
        assertEquals("-10", argsMap.get("m"));
    }

    @Test
    public void validate_fully_input() {
        String inputArgs = "-l -d 20.1 -s a1,a2,a3 -m -10 -r -10.1,-.2,1.3 w wrong";
        Map<String, String> argsMap = Analyzer.analyze(inputArgs);
        assertEquals("", argsMap.get("l"));
        assertEquals("20.1", argsMap.get("d"));
        assertEquals("a1,a2,a3", argsMap.get("s"));
        assertEquals("-10", argsMap.get("m"));
        assertEquals("-10.1,-.2,1.3 w wrong", argsMap.get("r"));
    }

    @Test
    public void validate_wrong_input() {
        String inputArgs = "l d 20.1";
        Map<String, String> argsMap = Analyzer.analyze(inputArgs);
        assertTrue(argsMap.isEmpty());
    }

    @Test
    public void validate_empty() {
        String inputArgs = "";
        Map<String, String> argsMap = Analyzer.analyze(inputArgs);
        assertTrue(argsMap.isEmpty());
    }
}