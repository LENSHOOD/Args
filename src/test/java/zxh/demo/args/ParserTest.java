package zxh.demo.args;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import zxh.demo.args.exception.ParseException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validate_no_build() {
        expectedException.expect(ParseException.class);
        expectedException.expectMessage("No build before parse!");
        new Parser().parse("");
    }

    @Test
    public void validate_boolean() {
        String args = "-b";
        Parser parser = new Parser();
        parser.build("b:boolean");
        ParserResult result = parser.parse(args);
        assertEquals(Boolean.TRUE, result.getValueByFlag("b"));
    }

    @Test
    public void validate_wrong_boolean() {
        String args = "-b yes";
        Parser parser = new Parser();
        parser.build("b:boolean");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage("Invalid value yes, for boolean flag b!");
        parser.parse(args);
    }

    @Test
    public void validate_boolean_default() {
        String args = "";
        Parser parser = new Parser();
        parser.build("b:boolean");
        ParserResult result = parser.parse(args);
        assertEquals(Boolean.FALSE, result.getValueByFlag("b"));
    }

    @Test
    public void validate_integer_double() {
        String args = "-l 8080 -d -20.1";
        Parser parser = new Parser();
        parser.build("l:integer, d:double");
        ParserResult result = parser.parse(args);
        assertEquals(8080, result.getValueByFlag("l"));
        assertEquals(-20.1, result.getValueByFlag("d"));
    }

    @Test
    public void validate_integer_array() {
        String args = "-l 8,0,80,8080";
        Parser parser = new Parser();
        parser.build("l:integer, d:double");
        ParserResult result = parser.parse(args);
        assertArrayEquals(new Integer[]{8,0,80,8080}, (Integer[])result.getValueByFlag("l"));
    }

    @Test
    public void validate_wrong_integer() {
        String args = "-l a8b0c8d0 -d -20.1";
        Parser parser = new Parser();
        parser.build("l:integer, d:double");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage("Invalid value a8b0c8d0, for integer flag l!");
        parser.parse(args);
    }

    @Test
    public void validate_double_array() {
        String args = "-d -0.2,20.1,-1.1";
        Parser parser = new Parser();
        parser.build("l:integer, d:double");
        ParserResult result = parser.parse(args);
        assertArrayEquals(new Double[]{-0.2,20.1,-1.1}, (Double[])result.getValueByFlag("d"));
    }


    @Test
    public void validate_wrong_double() {
        String args = "-l 8080 -d";
        Parser parser = new Parser();
        parser.build("l:integer, d:double");
        expectedException.expect(ParseException.class);
        expectedException.expectMessage("Invalid value , for double flag d!");
        parser.parse(args);
    }


    @Test
    public void validate_default_integer_double() {
        String args = "";
        Parser parser = new Parser();
        parser.build("l:integer, d:double");
        ParserResult result = parser.parse(args);
        assertEquals(0, result.getValueByFlag("l"));
        assertEquals(0.0, result.getValueByFlag("d"));
    }


    @Test
    public void validate_minus_double_string() {
        String args = "-d -20.1";
        Parser parser = new Parser();
        parser.build("d:double");
        ParserResult result = parser.parse(args);
        assertEquals(-20.1, result.getValueByFlag("d"));
    }

    @Test
    public void validate_string() {
        String args = "-s iamstring";
        Parser parser = new Parser();
        parser.build("s:string");
        ParserResult result = parser.parse(args);
        assertEquals("iamstring", result.getValueByFlag("s"));
    }

    @Test
    public void validate_default_string() {
        String args = "";
        Parser parser = new Parser();
        parser.build("s:string");
        ParserResult result = parser.parse(args);
        assertEquals("", result.getValueByFlag("s"));
    }

    @Test
    public void validate_string_array() {
        String args = "-s i,am,string";
        Parser parser = new Parser();
        parser.build("s:string");
        ParserResult result = parser.parse(args);
        assertArrayEquals(new String[]{"i","am","string"}, (String[]) result.getValueByFlag("s"));
    }


    @Test
    public void validate_multiple_flag() {
        String args = "-b -l 8080 -d -20.1 -s iamstring -f -10.1,11,12.0";
        Parser parser = new Parser();
        parser.build("b:boolean, l:integer, d:double, s:string, f:double");
        ParserResult result = parser.parse(args);
        assertEquals(Boolean.TRUE, result.getValueByFlag("b"));
        assertEquals(8080, result.getValueByFlag("l"));
        assertEquals(-20.1, result.getValueByFlag("d"));
        assertEquals("iamstring", result.getValueByFlag("s"));
        assertArrayEquals(new Double[]{-10.1,11.0,12.0}, (Double[])result.getValueByFlag("f"));
    }
}