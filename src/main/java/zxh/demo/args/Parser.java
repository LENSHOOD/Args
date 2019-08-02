package zxh.demo.args;

import zxh.demo.args.internal.analyzer.Analyzer;
import zxh.demo.args.internal.schema.Schema;
import zxh.demo.args.internal.schema.type.SchemaType;
import zxh.demo.args.internal.schema.type.SchemaTypeException;

import java.lang.reflect.Array;
import java.util.Map;

/**
 * Parser:
 * @author zhangxuhai
 * @date 2019-07-25
*/
public class Parser {

    private Schema schema;

    class ParserException extends RuntimeException {
        ParserException(String message) {
            super(message);
        }
    }


    public Parser(String schemaString) {
        schema = new Schema(schemaString);
    }

    public ParserResult parse(String inputArgs) {
        inputArgs = inputArgs.trim();

        ParserResult result = buildParserResult(schema.getFlagDefaults());
        try {
            Analyzer.analyze(inputArgs).forEach((k, v) ->
                    result.setFlagAndValue(k, parseValue(k, v))
            );

        } catch (Analyzer.AnalyzeException | SchemaTypeException e) {
            throw new ParserException(e.getMessage());
        }
        return result;
    }

    private Object parseValue(String flag, String inputValue) {
        String[] valueOfArray = inputValue.split(",");
        int len = valueOfArray.length;

        SchemaType schemaType = schema.get(flag);
        if (schemaType == null) {
            throw new ParserException(String.format("Wrong flag: %s", flag));
        }
        if (len == 1) {
            return schema.get(flag).parse(inputValue);
        }

        Object resultArray = Array.newInstance(schema.get(flag).getDefault().getClass(), len);
        for (int i = 0; i < len; i++) {
            Array.set(resultArray, i, schema.get(flag).parse(valueOfArray[i].trim()));
        }
        return resultArray;

    }

    private ParserResult buildParserResult(Map<String, Object> flagDefaultValueMap) {
        ParserResult parserResult = new ParserResult();
        flagDefaultValueMap.forEach(parserResult::setFlagAndValue);
        return parserResult;
    }
}
