package com.zxh.args.parser.internal;

import com.zxh.args.parser.internal.exception.WrongArgsException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analyzer:
 * @author zhangxuhai
 * @date 2019-06-30
*/
public class Analyzer {

    private static final Pattern MATCH_MINUS_CHAR_FLAG = Pattern.compile("-[a-zA-Z]");

    public List<String[]> analyze(String args) {
        // truncate space and test if the first character is '-'
        args = args.trim();
        if (args.isEmpty()) {
            return Collections.emptyList();
        }

        if (!args.startsWith("-")) {
            throw new WrongArgsException("Wrong arg flag, flag should be prefix by minus character");
        }

        // match flag and get flag index range
        Matcher matcher = MATCH_MINUS_CHAR_FLAG.matcher(args);
        List<int[]> flagIndexRanges = new ArrayList<>();
        while (matcher.find()) {
            flagIndexRanges.add(new int[]{matcher.start(), matcher.end()});
        }

        List<String[]> flagAndValues = new ArrayList<>();
        for (int i = 0; i < flagIndexRanges.size(); i++) {
            int[] currentFlagIndexRange = flagIndexRanges.get(i);
            int[] nextFlagIndexRange =
                    (i == flagIndexRanges.size() - 1) ? new int[]{args.length(), 0} : flagIndexRanges.get(i + 1);

            String flag = args.substring(currentFlagIndexRange[0] + 1, currentFlagIndexRange[1]).trim();
            String value = args.substring(currentFlagIndexRange[1], nextFlagIndexRange[0]).trim();

            // deal with Boolean flag
            if (value.length() == 0) {
                value = "";
            }

            // deal with error value
            if (value.contains(" ")) {
                throw new WrongArgsException(String.format("Wrong arg: %s, which value is %s %n", flag, value));
            }

            flagAndValues.add(new String[]{flag, value});
        }
        return flagAndValues;
    }
}
