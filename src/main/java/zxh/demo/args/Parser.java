package zxh.demo.args;

import zxh.demo.args.internal.Analyzer;
import zxh.demo.args.internal.Schema;

import java.lang.reflect.*;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Parser:
 * @author zhangxuhai
 * @date 2019-08-27
*/
interface IParser {
    void parse(String input);

    Object get(String flag);
}

public class Parser implements IParser {

    private static class InnerParser implements IParser {

        private Schema schema;
        private Map<String, Object> flagValueMap;

        InnerParser(String schemaString) {
            schema = new Schema(schemaString);
            flagValueMap = schema.getFlagAndDefaultValues();
        }

        @Override
        public void parse(String input) {
            Analyzer.analyze(input).forEach((flag, stringValue) ->
                    flagValueMap.put(flag, parseValueBySchema(flag, stringValue)));
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

        @Override
        public Object get(String flag) {
            return flagValueMap.get(flag);
        }
    }

    private static class ParserExceptionHandler implements InvocationHandler {
        private Object obj;

        ParserExceptionHandler(Object obj) {
            this.obj = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            try {
                return method.invoke(obj, args);
            } catch (InvocationTargetException e) {
                throw new ParserException(e.getTargetException().getMessage());
            } catch (IllegalAccessException e) {
                throw new ParserException("Internal Error.");
            }
        }
    }

    private IParser parser;

    private Parser(IParser parser) {
        this.parser = parser;
    }

    public static Parser build(String schemaString) {
        Parser parser;
        try {
            parser = new Parser((IParser) Proxy.newProxyInstance(
                            Parser.class.getClassLoader(),
                            new Class[]{IParser.class},
                            new ParserExceptionHandler(new InnerParser(schemaString))));
        } catch (Exception e) {
            throw new ParserException(e.getMessage());
        }

        return parser;
    }

    @Override
    public void parse(String input) {
        parser.parse(input);
    }

    @Override
    public Object get(String flag) {
        return parser.get(flag);
    }
}
