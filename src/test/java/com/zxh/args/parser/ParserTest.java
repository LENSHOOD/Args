package com.zxh.args.parser;

import com.zxh.args.parser.exception.ParseException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ParserTest {

    private Parser parser;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        parser = new Parser(getClass().getClassLoader().getResource("test-schema-1.yml").getPath());
    }

    @Test
    public void validate_parse_normal_input() {
        // normal input
        String args = "-l -p 8080 -d /usr/logs";
        ParserResult normalResult = parser.parse(args);
        assertEquals(true, normalResult.getValueByFlag("l"));
        assertEquals(8080, normalResult.getValueByFlag("p"));
        assertEquals("/usr/logs", normalResult.getValueByFlag("d"));
    }

    @Test
    public void validate_wrong_input() {
        // wrong input
        String wrongArgs = "-l -b 8080 -d /usr/logs";
        expectedException.expect(ParseException.class);
        parser.parse(wrongArgs);
    }

    @Test
    public void validate_wrong_input_type() {
        // wrong input type
        String wrongTypeArgs = "-l -p 8080abcd -d /usr/logs";
        expectedException.expect(ParseException.class);
        parser.parse(wrongTypeArgs);
    }

    @Test
    public void validate_array_input() {
        // array input
        String arrayArgs = "-i 1,2,3 -d 1.0,2.1,3.2 -s 1,2.0,3ab";
        parser = new Parser(getClass().getClassLoader().getResource("test-schema-array.yml").getPath());
        ParserResult arrayResult = parser.parse(arrayArgs);
        assertArrayEquals(new Integer[]{1,2,3}, (Integer[])arrayResult.getValueByFlag("i"));
        assertArrayEquals(new Double[]{1.0,2.1,3.2}, (Double[])arrayResult.getValueByFlag("d"));
        assertArrayEquals(new String[]{"1","2.0","3ab"}, (String[])arrayResult.getValueByFlag("s"));
    }
}