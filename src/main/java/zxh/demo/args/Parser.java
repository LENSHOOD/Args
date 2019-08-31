package zxh.demo.args;

import zxh.demo.args.internal.Analyzer;
import zxh.demo.args.internal.Schema;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

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
            Analyzer.analyze(input).forEach((flag, stringValue) ->
                    flagValueMap.put(flag, parseValueBySchema(flag, stringValue)));
        } catch (Exception e) {
            throw new ParserException(e.getMessage());
        }
    }

    private Object parseValueBySchema(String flag, String stringValue) {
        Object defaultValue = schema.getTypeByFlag(flag).getDefault();
        if (defaultValue == null) {
            return null;
        }

        String[] valueArray = stringValue.split(",");
        Object resultArray = Array.newInstance(defaultValue.getClass(), valueArray.length);
        IntStream.range(0, valueArray.length).forEach(index ->
                Array.set(resultArray, index, schema.getTypeByFlag(flag).valueOf(valueArray[index])));

        if (valueArray.length == 1) {
            return Array.get(resultArray, 0);
        }

        return resultArray;
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
