package com.zxh.args.parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {

    private Parser parser;

    @Test
    public void validate_parse() {
        // normal input
        String args = "-l -p 8080 -d /usr/logs";
        parser = new Parser(getClass().getClassLoader().getResource("test-schema-1.yml").getPath());
        ParserResult normalResult = parser.parse(args);
        assertEquals(true, normalResult.getValueByFlag("l"));
        assertEquals(8080, normalResult.getValueByFlag("p"));
        assertEquals("/usr/logs", normalResult.getValueByFlag("d"));

        // array input
        String arrayArgs = "-i 1,2,3 -d 1.0,2.1,3.2 -s 1,2.0,3ab";
        parser = new Parser(getClass().getClassLoader().getResource("test-schema-array.yml").getPath());
        ParserResult arrayResult = parser.parse(arrayArgs);
        assertArrayEquals(new Integer[]{1,2,3}, (Integer[])arrayResult.getValueByFlag("i"));
        assertArrayEquals(new Double[]{1.0,2.1,3.2}, (Double[])arrayResult.getValueByFlag("d"));
        assertArrayEquals(new String[]{"1","2.0","3ab"}, (String[])arrayResult.getValueByFlag("s"));
    }
}