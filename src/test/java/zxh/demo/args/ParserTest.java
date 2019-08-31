package zxh.demo.args;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void validate_boolean() {
        String input = "-b";
        Parser parser = new Parser();
        parser.build("b:boolean");
        parser.parse(input);
        assertEquals(Boolean.TRUE, parser.get("b"));
    }

    @Test
    public void validate_boolean_default() {
        Parser parser = new Parser();
        parser.build("b:boolean");
        parser.parse("-f others");
        assertEquals(Boolean.FALSE, parser.get("b"));
    }

    @Test
    public void validate_boolean_wrong_valueOf() {
        String input = "-b yes";
        Parser parser = new Parser();
        parser.build("b:boolean");
        expectedException.expect(ParserException.class);
        expectedException.expectMessage("Invalid input value: yes");
        parser.parse(input);
    }

    @Test
    public void validate_integer() {
        String input = "-i 8080";
        Parser parser = new Parser();
        parser.build("i:integer");
        parser.parse(input);
        assertEquals(8080, parser.get("i"));
    }

    @Test
    public void validate_integer_wrong_valueOf() {
        String input = "-i 8a0b8c0d";
        Parser parser = new Parser();
        parser.build("i:integer");
        expectedException.expect(ParserException.class);
        expectedException.expectMessage("Invalid input value: 8a0b8c0d");
        parser.parse(input);
    }

    @Test
    public void validate_integer_array() {
        String input = "-i 8080,999,-11";
        Parser parser = new Parser();
        parser.build("i:integer");
        parser.parse(input);
        assertArrayEquals(new Integer[]{8080, 999, -11}, (Integer[]) parser.get("i"));
    }

    @Test
    public void validate_double() {
        String input = "-d -20.1";
        Parser parser = new Parser();
        parser.build("d:double");
        parser.parse(input);
        assertEquals(-20.1, parser.get("d"));
    }

    @Test
    public void validate_double_wrong_input() {
        String input = "-d 17.a";
        Parser parser = new Parser();
        parser.build("d:double");
        expectedException.expect(ParserException.class);
        expectedException.expectMessage("Invalid input value: 17.a");
        parser.parse(input);
    }

    @Test
    public void validate_double_array() {
        String input = "-d -10.1,11,12.0";
        Parser parser = new Parser();
        parser.build("d:double");
        parser.parse(input);
        assertArrayEquals(new Double[]{-10.1,11.0,12.0}, (Double[]) parser.get("d"));
    }

    @Test
    public void validate_string() {
        String input = "-s iamstring";
        Parser parser = new Parser();
        parser.build("s:string");
        parser.parse(input);
        assertEquals("iamstring", parser.get("s"));
    }

    @Test
    public void validate_string_default() {
        Parser parser = new Parser();
        parser.build("s:string");
        parser.parse("-f others");
        assertEquals("", parser.get("s"));
    }

    @Test
    public void validate_string_array() {
        String input = "-s i,am,string";
        Parser parser = new Parser();
        parser.build("s:string");
        parser.parse(input);
        assertArrayEquals(new String[]{"i", "am", "string"}, (String[])parser.get("s"));
    }

    @Test
    public void validate_all_type() {
        String input = "-b -i 8080 -s iamstring -d -10.1,11,12.0";
        Parser parser = new Parser();
        parser.build("b:boolean, s:string, d:double, i:integer");
        parser.parse(input);
        assertEquals(Boolean.TRUE, parser.get("b"));
        assertEquals(8080, parser.get("i"));
        assertEquals("iamstring", parser.get("s"));
        assertArrayEquals(new Double[]{-10.1,11.0,12.0}, (Double[])parser.get("d"));
    }
    
    @Test
    public void validate_wrong_flag_format() {
        String input = "-i 8080 d -20.1";
        Parser parser = new Parser();
        parser.build("i:integer, d:double");
        expectedException.expect(ParserException.class);
        expectedException.expectMessage("Invalid integer type flag i of value: 8080 d -20.1");
        parser.parse(input);
    }

    @Test
    public void validate_parser_not_init() {
        expectedException.expect(ParserException.class);
        expectedException.expectMessage("Invalid parser: not call build() before parse().");
        new Parser().parse("");
    }
}