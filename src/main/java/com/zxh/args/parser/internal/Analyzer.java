package com.zxh.args.parser.internal;

import com.zxh.args.parser.internal.exception.WrongArgsException;

import java.util.*;

/**
 * Analyzer:
 * @author zhangxuhai
 * @date 2019-06-30
*/
public class Analyzer {

    public List<String[]> analyze(String args) {
        // truncate space and test if the first character is '-'
        args = args.trim();
        if (!args.startsWith("-")) {
            throw new WrongArgsException("Wrong arg flag, flag should be prefix by minus character");
        }
        args = args.substring(1);

        String[] rawArgArray = args.split("-");
        List<String[]> flagAndValues = new ArrayList<>();
        for (String flagAndValue : rawArgArray) {
            String[] flagAndValueArray = flagAndValue.split(" ");
            if (flagAndValueArray.length > 2) {
                throw new WrongArgsException(String.format("Wrong arg flag: %s", flagAndValueArray[0]));
            }

            flagAndValues.add(new String[] {flagAndValueArray[0],
                            flagAndValueArray.length == 1 ? "" : flagAndValueArray[1]});

        }

        return flagAndValues;
    }
}
