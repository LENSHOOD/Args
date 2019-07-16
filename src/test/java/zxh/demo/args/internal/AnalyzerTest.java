package zxh.demo.args.internal;

import org.junit.Test;
import zxh.demo.args.internal.exception.AnalyzeException;

import static org.junit.Assert.*;

public class AnalyzerTest {

    @Test
    public void validate_empty() {
        assertTrue(Analyzer.analyze("").isEmpty());
    }

    @Test
    public void validate_empty_string() {
        String args = "-b";
        assertEquals("", Analyzer.analyze(args).get("b"));
    }
    
    @Test
    public void validate_integer_string() {
        String args = "-l 8080";
        assertEquals("8080", Analyzer.analyze(args).get("l"));
    }
    
    @Test
    public void validate_minus_double_string() {
        String args = "-d -20.1";
        assertEquals("-20.1", Analyzer.analyze(args).get("d"));
    }
    
    @Test
    public void validate_string() {
        String args = "-s iamstring";
        assertEquals("iamstring", Analyzer.analyze(args).get("s"));
    }

    @Test
    public void validate_multiple_flag() {
        String args = "-b -l 8080 -d -20.1 -s iamstring -f -10.1,11,12.0";
        assertEquals("", Analyzer.analyze(args).get("b"));
        assertEquals("8080", Analyzer.analyze(args).get("l"));
        assertEquals("-20.1", Analyzer.analyze(args).get("d"));
        assertEquals("iamstring", Analyzer.analyze(args).get("s"));
        assertEquals("-10.1,11,12.0", Analyzer.analyze(args).get("f"));
    }

    @Test
    public void validate_flag_value_no_space() {
        String args = "-l8080";
        assertEquals("8080", Analyzer.analyze(args).get("l"));
    }

    @Test(expected = AnalyzeException.class)
    public void validate_wrong_flag_no_minus() {
        String args = "-l 8080 d -20.1";
        Analyzer.analyze(args);
    }
}