package zxh.demo.args;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void validate_boolean() {
        String input = "-b";
        Parser parser = new Parser();
        parser.parse(input);
        assertEquals("", parser.get("b"));
    }
    
    @Test
    public void validate_integer() {
        String input = "-i 8080";
        Parser parser = new Parser();
        parser.parse(input);
        assertEquals("8080", parser.get("i"));
    }
    
    @Test
    public void validate_double() {
        String input = "-d -20.1";
        Parser parser = new Parser();
        parser.parse(input);
        assertEquals("-20.1", parser.get("d"));
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
    public void validate_doubleArray() {
        String input = "-d -10.1,11,12.0";
        Parser parser = new Parser();
        parser.parse(input);
        assertEquals("-10.1,11,12.0", parser.get("d"));
    }
    
    @Test
    public void validate_allType() {
        String input = "-b -i 8080 -s iamstring -d -10.1,11,12.0";
        Parser parser = new Parser();
        parser.parse(input);
        assertEquals("", parser.get("b"));
        assertEquals("8080", parser.get("i"));
        assertEquals("iamstring", parser.get("s"));
        assertEquals("-10.1,11,12.0", parser.get("d"));
    }
    
    @Test
    public void validate_wrongFlagFormat() {
        String input = "-i 8080 d -20.1";
        Parser parser = new Parser();

        expectedException.expect(ParserException.class);
        expectedException.expectMessage("Invalid integer type flag i of value: 8080 d -20.1");
        parser.parse(input);
    }
}