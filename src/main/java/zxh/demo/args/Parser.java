package zxh.demo.args;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser:
 * @author zhangxuhai
 * @date 2019-08-27
*/
public class Parser {

    private static final Pattern MINUS_FLAG_SPACE = Pattern.compile("-[a-zA-Z]\\s");
    private Map<String, String> flagValueMap = new HashMap<>();

    public void parse(String input) {
        // avoid last flag do not have space
        input = input + " ";

        List<Integer> minusFlagSpaceIndexes = findMinusFlagSpaceIndexes(input);

        for (int i = 0; i < minusFlagSpaceIndexes.size(); i++) {
            Integer minusFlagSpaceIndex = minusFlagSpaceIndexes.get(i);
            int valueStartIndex = minusFlagSpaceIndex + 2 >= input.length() ? input.length()-1 : minusFlagSpaceIndex + 2;
            int valueEndIndex = i == minusFlagSpaceIndexes.size() - 1
                    ? input.length() - 1
                    : minusFlagSpaceIndexes.get(i + 1);

            String flag = input.substring(minusFlagSpaceIndex + 1, minusFlagSpaceIndex + 2);
            String value = input.substring(valueStartIndex, valueEndIndex).trim();

            if (value.contains(" ")) {
                throw new ParserException(String.format("Invalid integer type flag %s of value: %s", flag, value));
            }

            flagValueMap.put(flag, value);
        }
    }

    private List<Integer> findMinusFlagSpaceIndexes(String input) {
        List<Integer> minusFlagSpaceIndexes = new ArrayList<>();
        Matcher flagValueMatcher = MINUS_FLAG_SPACE.matcher(input);
        while (flagValueMatcher.find()) {
            minusFlagSpaceIndexes.add(input.indexOf(flagValueMatcher.group()));
        }

        return minusFlagSpaceIndexes;
    }

    public String get(String flag) {
        return flagValueMap.get(flag);
    }
}
