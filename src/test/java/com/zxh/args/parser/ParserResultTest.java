package com.zxh.args.parser;

import com.zxh.args.parser.exception.ParserResultException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ParserResultTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validate_setFlagValue() {
        // null flag
        expectedException.expect(ParserResultException.class);
        new ParserResult().setFlagValue(null, "");

        // null value
        expectedException.expect(ParserResultException.class);
        new ParserResult().setFlagValue("f", null);

        // valid flag and value
        ParserResult validFlagAndValue = new ParserResult();
        validFlagAndValue.setFlagValue("f", 1234);
        assertEquals(1234, validFlagAndValue.getValueByFlag("f"));
    }

    @Test
    public void validate_getValueByFlag() {
        // no flag
        ParserResult noFlag = new ParserResult();
        assertNull(null, noFlag.getValueByFlag("f"));

        // null flag
        expectedException.expect(ParserResultException.class);
        ParserResult wrongFlag = new ParserResult();
        wrongFlag.getValueByFlag(null);

        // correct flag and value
        ParserResult correctFlagAndValue = new ParserResult();
        correctFlagAndValue.setFlagValue("a", "i am a");
        assertEquals("i am a", correctFlagAndValue.getValueByFlag("a"));
    }
}