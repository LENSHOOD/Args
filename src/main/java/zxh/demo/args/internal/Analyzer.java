package zxh.demo.args.internal;

import zxh.demo.args.internal.exception.AnalyzeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analyzer:
 * @author zhangxuhai
 * @date 2019-08-27
*/
public class Analyzer {
    private static final Pattern MINUS_FLAG_SPACE = Pattern.compile("-[a-zA-Z]\\s");

    private Analyzer() {
        // should be empty
    }

    public static Map<String, String> analyze(String input) {
        // avoid last flag do not have space
        input = input + " ";
        List<Integer> minusFlagSpaceIndexes = findMinusFlagSpaceIndexes(input);

        Map<String, String> flagValueMap = new HashMap<>(16);
        for (int i = 0; i < minusFlagSpaceIndexes.size(); i++) {
            Integer minusFlagSpaceIndex = minusFlagSpaceIndexes.get(i);
            int valueStartIndex = minusFlagSpaceIndex + 2 >= input.length() ? input.length()-1 : minusFlagSpaceIndex + 2;
            int valueEndIndex = i == minusFlagSpaceIndexes.size() - 1
                    ? input.length() - 1
                    : minusFlagSpaceIndexes.get(i + 1);

            String flag = input.substring(minusFlagSpaceIndex + 1, minusFlagSpaceIndex + 2);
            String value = input.substring(valueStartIndex, valueEndIndex).trim();

            if (value.contains(" ")) {
                throw new AnalyzeException(String.format("Invalid integer type flag %s of value: %s", flag, value));
            }

            flagValueMap.put(flag, value);
        }

        return flagValueMap;
    }

    private static List<Integer> findMinusFlagSpaceIndexes(String input) {
        List<Integer> minusFlagSpaceIndexes = new ArrayList<>();
        Matcher flagValueMatcher = MINUS_FLAG_SPACE.matcher(input);
        while (flagValueMatcher.find()) {
            minusFlagSpaceIndexes.add(input.indexOf(flagValueMatcher.group()));
        }

        return minusFlagSpaceIndexes;
    }
}
