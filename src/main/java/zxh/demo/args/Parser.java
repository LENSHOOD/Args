package zxh.demo.args;

import zxh.demo.args.exception.ParserException;
import zxh.demo.args.internal.Analyzer;
import zxh.demo.args.internal.Schema;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

/**
 * Parser:
 * @author zhangxuhai
 * @date 2019-07-09
*/
public class Parser {

    private Schema schema;

    public void build(String schemaString) {
        this.schema = new Schema(schemaString);
    }

    public ParserResult parse(String inputArgs) {
        if (Objects.isNull(schema)) {
            throw new ParserException("Invalid parser: not build with schema yet!");
        }

        ParserResult parserResult = initializeParserResult();
        Map<String, String> flagValueStringMap = Analyzer.analyze(inputArgs);
        flagValueStringMap.forEach(
                (k,v) -> {
                    String valueString = v;
                    Class clazz = schema.getClassBy(k);

                    if (Objects.isNull(clazz)) {
                        throw new ParserException(String.format("Wrong flag value pair: -%s %s, invalid flag!", k, v));
                    }

                    if (clazz == Boolean.class) {
                        valueString = "true";
                    }

                    Object value = clazz.isArray() ? valueOfArray(clazz, valueString) : valueOf(clazz, valueString);
                    parserResult.setValueWithFlag(k, value);
                }
        );

        return parserResult;
    }

    private ParserResult initializeParserResult() {
        return new ParserResult(schema.getAllFlagWithDefaultValue());
    }

    private Object valueOfArray(Class clazz, String valueString) {
        String[] valueStringArray = valueString.trim().split(",");
        Object resultArray = Array.newInstance(clazz.getComponentType(), valueStringArray.length);
        for (int i = 0; i < valueStringArray.length; i++) {
            Array.set(resultArray, i, valueOf(clazz.getComponentType(), valueStringArray[i]));
        }
        return resultArray;
    }

    private Object valueOf(Class clazz, String valueString) {

        // String not need to convert
        if (clazz == String.class) {
            return valueString;
        }

        try {
            //noinspection unchecked
            return clazz.getDeclaredMethod("valueOf", String.class).invoke(null, valueString);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new ParserException("Internal Error!", e);
        } catch (InvocationTargetException e) {
            throw new ParserException("Invalid value: " + valueString + ", wrong type!");
        }
    }
}
