package com.zxh.args.parser.internal;

import com.zxh.args.parser.internal.exception.WrongArgsException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class AnalyzerTest {

    private Analyzer analyzer;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        analyzer = new Analyzer();
    }

    @Test
    public void validate_analyze() {
        // single empty
        assertArrayEquals(new String[]{"l", ""}, analyzer.analyze("-l").get(0));

        // single number
        assertArrayEquals(new String[]{"p", "8080"}, analyzer.analyze("-p 8080").get(0));

        // single string
        assertArrayEquals(new String[]{"d", "/usr/logs"}, analyzer.analyze("-d /usr/logs").get(0));

        // multiple input
        assertArrayEquals(new String[] {"l", ""}, analyzer.analyze("-l -p 8080 -d /usr/logs").get(0));
        assertArrayEquals(new String[] {"p", "8080"}, analyzer.analyze("-l -p 8080 -d /usr/logs").get(1));
        assertArrayEquals(new String[] {"d", "/usr/logs"}, analyzer.analyze("-l -p 8080 -d /usr/logs").get(2));


        // wrong args
        expectedException.expect(WrongArgsException.class);
        analyzer.analyze("l -p 8080 -d /usr/logs");

        expectedException.expect(WrongArgsException.class);
        analyzer.analyze("-l -p8080 -d /usr/logs");

        expectedException.expect(WrongArgsException.class);
        analyzer.analyze("-l -p 8080 8081 -d /usr/logs");

    }
}