package zxh.demo.args.internal.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analyzer:
 * @author zhangxuhai
 * @date 2019-07-28
*/
public class Analyzer {

    private static final Pattern MINUS_AND_FLAG = Pattern.compile("-[a-zA-Z]");

    private Analyzer() {
        // empty
    }

    public static class AnalyzeException extends RuntimeException {
        AnalyzeException(String message) {
            super(message);
        }
    }

    public static Map<String, String> analyze(String input) {
        Matcher matcher = MINUS_AND_FLAG.matcher(input);
        List<String> minusFlags = getMinusFlags(matcher);
        return getFlagValuePairs(input, minusFlags);
    }

    private static List<String> getMinusFlags(Matcher matcher) {
        List<String> minusFlags = new ArrayList<>();
        while (matcher.find()) {
            minusFlags.add(matcher.group());
        }
        return minusFlags;
    }

    private static Map<String, String> getFlagValuePairs(String input, List<String> minusFlags) {
        Map<String, String> resultMap = new HashMap<>();
        for (int i = 0; i < minusFlags.size(); i++) {
            int currentValueStartIndex = input.indexOf(minusFlags.get(i)) + 2;
            int currentValueEndIndex = i < minusFlags.size() - 1
                    ? input.indexOf(minusFlags.get(i + 1))
                    : input.length();

            String flag = minusFlags.get(i).substring(1, 2);
            String value = input.substring(currentValueStartIndex, currentValueEndIndex).trim();
            if (value.contains(" ")) {
                throw new AnalyzeException(String.format("Invalid flag %s of value: %s", flag, value));
            }
            resultMap.put(flag, value);
        }

        return resultMap;
    }
}
