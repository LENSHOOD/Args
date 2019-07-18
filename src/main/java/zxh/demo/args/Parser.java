package zxh.demo.args;

import zxh.demo.args.exception.ParseException;
import zxh.demo.args.internal.Analyzer;
import zxh.demo.args.internal.schema.Schema;
import zxh.demo.args.internal.schema.SchemaType;
import zxh.demo.args.internal.schema.internal.exception.InvalidValueException;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.Objects;

/**
 * zxh.demo.args.Parser:
 * @author zhangxuhai
 * @date 2019-07-16
*/
public class Parser {

    private Schema schema;

    public void build(String schemaString) {
        schema = new Schema(schemaString);
    }

    public ParserResult parse(String inputArgs) {
        validateSchema();

        ParserResult result = buildDefaultParserResult();
        Map<String, String> flagValueMap = Analyzer.analyze(inputArgs);
        flagValueMap.forEach(
                (flag,valueOfString) -> {
                    SchemaType type = schema.getTypeByFlag(flag);

                    if (Objects.isNull(type)) {
                        throw new ParseException("Invalid flag: not found in schema!");
                    }

                    try {
                        result.setFlagAndValue(flag, convertStringValueToSchemaTypeValue(valueOfString, type));
                    } catch (InvalidValueException e) {
                        throw new ParseException(
                                String.format("Invalid value %s, for %s flag %s!", valueOfString, type.getName(), flag));
                    }
                }
        );

        return result;
    }

    private Object convertStringValueToSchemaTypeValue(String valueOfString, SchemaType type) {
        String[] valueArray = valueOfString.split(",");
        Object resultArray = Array.newInstance(type.getDefault().getClass(), valueArray.length);
        for (int i = 0; i < valueArray.length; i++) {
            Array.set(resultArray, i, type.valueOf(valueArray[i]));
        }

        return valueArray.length == 1 ? Array.get(resultArray, 0) : resultArray;
    }

    private ParserResult buildDefaultParserResult() {
        ParserResult result = new ParserResult();
        schema.getDefaultSchemaMap().forEach(result::setFlagAndValue);
        return result;
    }

    private void validateSchema() {
        if (Objects.isNull(schema)) {
            throw new ParseException("No build before parse!");
        }
    }
}
