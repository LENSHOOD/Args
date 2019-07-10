package zxh.demo.args.internal;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analyzer:
 * @author zhangxuhai
 * @date 2019-07-09
*/
public class Analyzer {
    private static final Pattern MINUS_ONE_CHAR_PATTERN = Pattern.compile("-[a-zA-Z]");

    public static Map<String, String> analyze(String inputArgs) {
        inputArgs = inputArgs.trim();
        if (inputArgs.length() == 0) {
            return Collections.emptyMap();
        }

        List<String> flagsWithMinus = matchFlags(inputArgs);
        return getFlagValueMapBy(inputArgs, flagsWithMinus);
    }

    private static Map<String, String> getFlagValueMapBy(String inputArgs, List<String> flagsWithMinus) {
        Map<String, String> resultMap = new HashMap<>(16);
        for (int i = 0; i < flagsWithMinus.size(); i++) {
            int flagLastIndex = inputArgs.indexOf(flagsWithMinus.get(i)) + 1;
            int nextFlagFirstIndex = i == flagsWithMinus.size() - 1 ? inputArgs.length() : inputArgs.indexOf(flagsWithMinus.get(i + 1));

            String value = flagLastIndex >= nextFlagFirstIndex
                    ? ""
                    : inputArgs.substring(flagLastIndex + 1, nextFlagFirstIndex).trim();

            resultMap.put(flagsWithMinus.get(i).substring(1), value);
        }
        return resultMap;
    }

    private static List<String> matchFlags(String inputArgs) {
        Matcher inputArgsMatcher = MINUS_ONE_CHAR_PATTERN.matcher(inputArgs);
        List<String> flagsWithMinus = new ArrayList<>();
        while (inputArgsMatcher.find()) {
            flagsWithMinus.add(inputArgsMatcher.group());
        }
        return flagsWithMinus;
    }


}
