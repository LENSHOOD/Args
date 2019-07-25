package zxh.demo.args;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser:
 * @author zhangxuhai
 * @date 2019-07-25
*/
public class Parser {

    class ParserException extends RuntimeException {
        public ParserException(String message) {
            super(message);
        }
    }


    public Parser(String schemaString) {

    }

    public ParserResult parse(String inputArgs) {
        inputArgs = inputArgs.trim();
        Pattern pattern = Pattern.compile("-[a-zA-Z]");
        Matcher matcher = pattern.matcher(inputArgs);
        List<String> minusFlags = new ArrayList<>();
        while (matcher.find()) {
            minusFlags.add(matcher.group());
        }

        ParserResult parserResult = new ParserResult();
        for (int i = 0; i < minusFlags.size(); i++) {
            int currentValueStartIndex = inputArgs.indexOf(minusFlags.get(i)) + 2;
            int currentValueEndIndex = i < minusFlags.size() - 1
                    ? inputArgs.indexOf(minusFlags.get(i + 1))
                    : inputArgs.length();

            parserResult.setFlagAndValue(minusFlags.get(i).substring(1,2),
                    inputArgs.substring(currentValueStartIndex, currentValueEndIndex).trim());
        }
        return parserResult;
    }
}
