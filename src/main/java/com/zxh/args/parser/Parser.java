package com.zxh.args.parser;

import com.zxh.args.parser.exception.ParseException;
import com.zxh.args.parser.exception.ParserInternalException;
import com.zxh.args.parser.internal.Analyzer;
import com.zxh.args.parser.internal.Schema;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

/**
 * Parser:
 * @author zhangxuhai
 * @date 2019-07-01
*/
public class Parser {

    private Schema schema;
    private Analyzer analyzer = new Analyzer();

    public Parser(String schemaFileAddr) {
        schema = new Schema(schemaFileAddr);
    }

    public ParserResult parse(String args) {
        ParserResult result = new ParserResult();
        analyzer.analyze(args).forEach(
                flagAndStringValue -> {
                    String flag = flagAndStringValue[0];
                    Class clazz = schema.getFlagClass(flag);

                    if (Objects.isNull(clazz)) {
                        throw new ParseException(String.format("Wrong flag: %s!%n", flag));
                    }

                    String valueString = flagAndStringValue[1];
                    result.setFlagValue(flag,
                            clazz.isArray() ? parseForArray(clazz, valueString) : parseForSingleton(clazz, valueString));
                }
        );
        return result;
    }

    private Object parseForArray(Class clazz, String valueString) {
        String[] valueStringArray = valueString.split(",");
        Object valueArray = Array.newInstance(clazz.getComponentType(), valueStringArray.length);
        for (int i = 0; i < valueStringArray.length; i++) {
            Array.set(valueArray, i, parseForSingleton(clazz.getComponentType(), valueStringArray[i]));
        }
        return valueArray;
    }

    private Object parseForSingleton(Class clazz, String valueString) {
        Object value;

        if (clazz == Boolean.class && valueString.isEmpty()) {
            valueString = "true";
        }

        if (clazz == String.class) {
            value = valueString;
        } else {
            value = valueOf(clazz, valueString);
        }

        return value;
    }

    private Object valueOf(Class clazz, String valueString) {
        try {
            return clazz.getMethod("valueOf", String.class).invoke(null, valueString);
        } catch (InvocationTargetException e1) {
            if (e1.getCause() instanceof NumberFormatException) {
                throw new ParseException(String.format("Wrong value type: %s %n", valueString));
            }

            throw new ParserInternalException("Parser Internal Error, please contact developer.", e1);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            throw new ParserInternalException("Parser Internal Error, please contact developer.", e);
        }
    }
}
