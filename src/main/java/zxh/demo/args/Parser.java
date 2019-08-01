package zxh.demo.args;

import zxh.demo.args.internal.analyzer.Analyzer;
import zxh.demo.args.internal.schema.Schema;
import zxh.demo.args.internal.schema.type.SchemaTypeException;

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
            Analyzer.analyze(inputArgs).forEach(
                    (k, v) -> result.setFlagAndValue(k, schema.get(k).parse(v))
            );

        } catch (Analyzer.AnalyzeException | SchemaTypeException e) {
            throw new ParserException(e.getMessage());
        }
        return result;
    }

    private ParserResult buildParserResult(Map<String, Object> flagDefaultValueMap) {
        ParserResult parserResult = new ParserResult();
        flagDefaultValueMap.forEach(parserResult::setFlagAndValue);
        return parserResult;
    }
}
