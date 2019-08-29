package zxh.demo.args;

import zxh.demo.args.internal.Analyzer;
import zxh.demo.args.internal.Schema;

import java.util.Map;
import java.util.Objects;

/**
 * Parser:
 * @author zhangxuhai
 * @date 2019-08-27
*/
public class Parser {
    private Schema schema = null;
    private Map<String, Object> flagValueMap;

    public void parse(String input) {
        if (Objects.isNull(schema)) {
            throw new ParserException("Invalid parser: not call build() before parse().");
        }

        try {
            flagValueMap.putAll(Analyzer.analyze(input));
        } catch (Exception e) {
            throw new ParserException(e.getMessage());
        }
    }

    public Object get(String flag) {
        return flagValueMap.get(flag);
    }

    public void build(String schemaString) {
        try {
            schema = new Schema(schemaString);
            initSchemaDefaultValue();
        } catch (Exception e) {
            throw new ParserException(e.getMessage());
        }
    }

    private void initSchemaDefaultValue() {
        flagValueMap = schema.getFlagAndDefaultValues();
    }
}
