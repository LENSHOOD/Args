package zxh.demo.args.internal;

import zxh.demo.args.internal.exception.AnalyzeException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analyzer:
 * @author zhangxuhai
 * @date 2019-07-15
*/
public class Analyzer {
    private static final Pattern MINUS_CHAR_FLAG = Pattern.compile("-[a-zA-Z]");

    private Analyzer() {
        // remain empty
    }

    public static Map<String, String> analyze(String args) {

        if (Objects.isNull(args) || args.isEmpty()) {
            return Collections.emptyMap();
        }

        List<String> flags = matchFlags(args);
        return getFlagAndValueMap(args, flags);
    }

    private static List<String> matchFlags(String args) {
        Matcher matcher = MINUS_CHAR_FLAG.matcher(args);
        List<String> flags = new ArrayList<>();
        while (matcher.find()) {
            flags.add(matcher.group());
        }
        return flags;
    }

    private static Map<String, String> getFlagAndValueMap(String args, List<String> flags) {
        Map<String, String> resultMap = new HashMap<>();
        for (int i = 0; i < flags.size(); i++) {
            String currentMinusWithFlag = flags.get(i);
            int currentFlagEnd = args.indexOf(currentMinusWithFlag) + 2;
            int nextFlagStart = i < flags.size() - 1
                    ? args.indexOf(flags.get(i + 1))
                    : args.length();

            String currentValue = args.substring(currentFlagEnd, nextFlagStart).trim();
            if (currentValue.contains(" ")) {
                throw new AnalyzeException(
                        String.format("Invalid flag %s with value %s", currentMinusWithFlag, currentValue));
            }

            resultMap.put(currentMinusWithFlag.substring(1), currentValue);
        }
        return resultMap;
    }
}
