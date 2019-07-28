package zxh.demo.args;

import zxh.demo.args.internal.analyzer.Analyzer;

import java.util.Map;

/**
 * Parser:
 * @author zhangxuhai
 * @date 2019-07-25
*/
public class Parser {

    class ParserException extends RuntimeException {
        ParserException(String message) {
            super(message);
        }
    }


    public Parser(String schemaString) {

    }

    public ParserResult parse(String inputArgs) {
        inputArgs = inputArgs.trim();


        Map<String, String> analyzeResult;
        try {
            analyzeResult = Analyzer.analyze(inputArgs);
        } catch (Analyzer.AnalyzeException e) {
            throw new ParserException(e.getMessage());
        }
        return buildParserResult(analyzeResult);
    }

    private ParserResult buildParserResult(Map<String, String > flagValueMap) {
        ParserResult parserResult = new ParserResult();
        flagValueMap.forEach(parserResult::setFlagAndValue);
        return parserResult;
    }
}
