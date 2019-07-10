package zxh.demo.args;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import zxh.demo.args.exception.ParserException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String FULLY_INPUT = "-l -d 20.1 -s a1,a2,a3 -m -10 -r -10.1,-.2,1.3";
    private static final String SCHEMA_STRING = "l: boolean, d: double, s: string-array, m: integer, r: double-array";

    @Test
    public void validate_normal_input() {
        Parser parser = new Parser();
        parser.build(SCHEMA_STRING);
        ParserResult normalResult = parser.parse(FULLY_INPUT);
        assertEquals(Boolean.TRUE, normalResult.getArgValueByFlag("l"));
        assertEquals(20.1, normalResult.getArgValueByFlag("d"));
        assertArrayEquals(new String[]{"a1", "a2", "a3"}, (String[])normalResult.getArgValueByFlag("s"));
        assertEquals(-10, normalResult.getArgValueByFlag("m"));
        assertArrayEquals(new Double[]{-10.1, -0.2, 1.3}, (Double[])normalResult.getArgValueByFlag("r"));
    }

    @Test
    public void validate_not_build() {
        Parser parser = new Parser();
        expectedException.expect(ParserException.class);
        expectedException.expectMessage("Invalid parser: not build with schema yet!");
        parser.parse("");
    }

    @Test
    public void validate_wrong_type() {
        Parser parser = new Parser();
        parser.build(SCHEMA_STRING);

        String wrongTypeArgs = "-l -d a1";
        expectedException.expect(ParserException.class);
        expectedException.expectMessage("Invalid value: a1, wrong type!");
        parser.parse(wrongTypeArgs);
    }

    @Test
    public void validate_wrong_flag() {
        Parser parser = new Parser();
        parser.build(SCHEMA_STRING);

        String wrongTypeArgs = "-l -d -20.1 -p 123";
        expectedException.expect(ParserException.class);
        expectedException.expectMessage("Wrong flag value pair: -p 123, invalid flag!");
        parser.parse(wrongTypeArgs);
    }

    @Test
    public void validate_default_value() {
        Parser parser = new Parser();
        parser.build(SCHEMA_STRING);
        ParserResult result = parser.parse("");
        assertEquals(Boolean.FALSE, result.getArgValueByFlag("l"));
        assertEquals(0.0, result.getArgValueByFlag("d"));
        assertArrayEquals(new String[]{}, (String[])result.getArgValueByFlag("s"));
        assertEquals(0, result.getArgValueByFlag("m"));
        assertArrayEquals(new Double[]{}, (Double[])result.getArgValueByFlag("r"));
    }
}