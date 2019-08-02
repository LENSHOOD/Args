package zxh.demo.args;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * ParserTest:
 * @author zhangxuhai
 * @date 2019-07-25
*/
public class ParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validate_input_boolean_type_get_empty_string() {
        Parser parser = new Parser("b:boolean");
        Assert.assertEquals(Boolean.TRUE, parser.parse("-b").getFlag("b"));
    }
    
    @Test
    public void validate_integer_type_get_integer_string() {
        Parser parser = new Parser("l:integer");
        Assert.assertEquals(8080, parser.parse("-l 8080").getFlag("l"));
    }

    @Test
    public void validate_double_type_get_double_string() {
        Parser parser = new Parser("d:double");
        Assert.assertEquals(-20.1, parser.parse("-d -20.1").getFlag("d"));
    }
    
    @Test
    public void validate_string_type_get_string() {
        Parser parser = new Parser("s:string");
        Assert.assertEquals("iamstring", parser.parse("-s iamstring").getFlag("s"));
    }
    
    @Test
    public void validate_double_array_get_double_array_string() {
        Parser parser = new Parser("d:double");
        Assert.assertArrayEquals(new Double[]{-10.1,11.0,12.0}, (Double[]) parser.parse("-d -10.1,11,12.0").getFlag("d"));
    }

    @Test
    public void validate_double_array_get_string_array() {
        Parser parser = new Parser("s:string");
        Assert.assertArrayEquals(new String[]{"i", "am", "string"}, (String[]) parser.parse("-s i,am,string").getFlag("s"));
    }


    @Test
    public void validate_full_type_get_full_string() {
        Parser parser = new Parser("b:boolean, l:integer, d:double, s:string");
        ParserResult result = parser.parse("-b -l 8080 -s iamstring -d -10.1,11,12.0");
        Assert.assertEquals(Boolean.TRUE, result.getFlag("b"));
        Assert.assertEquals(8080, result.getFlag("l"));
        Assert.assertEquals("iamstring", result.getFlag("s"));
        Assert.assertArrayEquals(new Double[]{-10.1,11.0,12.0}, (Double[]) parser.parse("-d -10.1,11,12.0").getFlag("d"));
    }

    @Test
    public void validate_wrong_flag_get_exception() {
        Parser parser = new Parser("l:integer, d:double");
        expectedException.expect(Parser.ParserException.class);
        expectedException.expectMessage("Invalid flag l of value: 8080 d -20.1");
        parser.parse("-l 8080 d -20.1");
    }
}
