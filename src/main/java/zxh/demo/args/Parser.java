package zxh.demo.args;

import zxh.demo.args.internal.Analyzer;

import java.util.HashMap;
import java.util.Map;

/**
 * Parser:
 * @author zhangxuhai
 * @date 2019-08-27
*/
public class Parser {
    private Map<String, String> flagValueMap = new HashMap<>();

    public void parse(String input) {
        try {
            flagValueMap.putAll(Analyzer.analyze(input));
        } catch (Exception e) {
            throw new ParserException(e.getMessage());
        }
    }

    public String get(String flag) {
        return flagValueMap.get(flag);
    }
}
