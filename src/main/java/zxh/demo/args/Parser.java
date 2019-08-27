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
    private Map<String, String> schemaMap = new HashMap<>();
    private Map<String, String> flagValueMap = new HashMap<>();

    public void parse(String input) {
        try {
            initSchemaDefaultValue(schemaMap);
            flagValueMap.putAll(Analyzer.analyze(input));
        } catch (Exception e) {
            throw new ParserException(e.getMessage());
        }
    }

    public String get(String flag) {
        return flagValueMap.get(flag);
    }

    public void build(String schemaString) {
        String[] flagAndType = schemaString.split(":");
        String flag = flagAndType[0];
        String type = flagAndType[1];

        if ("string".equalsIgnoreCase(type)) {
            schemaMap.put(flag, "");
        }
    }

    private void initSchemaDefaultValue(Map<String, String> schemaMap) {
        flagValueMap.putAll(schemaMap);
    }
}
