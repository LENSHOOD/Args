package zxh.demo.args.internal.analyzer;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import zxh.demo.args.internal.analyzer.Analyzer;

import java.util.Map;

public class AnalyzerTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validate_input_boolean_type_get_empty_string() {
        Assert.assertEquals("", Analyzer.analyze("-b").get("b"));
    }

    @Test
    public void validate_integer_type_get_integer_string() {
        Assert.assertEquals("8080", Analyzer.analyze("-l 8080").get("l"));
    }

    @Test
    public void validate_double_type_get_double_string() {
        Assert.assertEquals("-20.1", Analyzer.analyze("-d -20.1").get("d"));
    }

    @Test
    public void validate_string_type_get_string() {
        Assert.assertEquals("iamstring", Analyzer.analyze("-s iamstring").get("s"));
    }

    @Test
    public void validate_double_array_get_double_array_string() {
        Assert.assertEquals("-10.1,11,12.0", Analyzer.analyze("-d -10.1,11,12.0").get("d"));
    }

    @Test
    public void validate_full_type_get_full_string() {
        Map<String, String> result = Analyzer.analyze("-b -l 8080 -s iamstring -d -10.1,11,12.0");
        Assert.assertEquals("", result.get("b"));
        Assert.assertEquals("8080", result.get("l"));
        Assert.assertEquals("iamstring", result.get("s"));
        Assert.assertEquals("-10.1,11,12.0", result.get("d"));
    }

    @Test
    public void validate_wrong_flag_get_exception() {
        expectedException.expect(Analyzer.AnalyzeException.class);
        expectedException.expectMessage("Invalid flag l of value: 8080 d -20.1");
        Analyzer.analyze("-l 8080 d -20.1");
    }
}