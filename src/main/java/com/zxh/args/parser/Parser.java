package com.zxh.args.parser;

import com.zxh.args.parser.internal.Analyzer;
import com.zxh.args.parser.internal.Schema;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

        List<String[]> flagAndStringValuePairs = analyzer.analyze(args);
        flagAndStringValuePairs.forEach(
                flagAndStringValue -> {
                    String flag = flagAndStringValue[0];
                    String valueString = flagAndStringValue[1];
                    Class clazz = schema.getFlagClass(flag);

                    try {
                        Object value;
                        if (clazz == Boolean.class && valueString.isEmpty()) {
                            valueString = "true";
                        }
                        if (clazz == String.class) {
                            value = valueString;
                        } else if (clazz.isArray()) {
                            String[] valueStringArray = valueString.split(",");
                            Object valueArray = Array.newInstance(clazz.getComponentType(), valueStringArray.length);
                            for (int i = 0; i < valueStringArray.length; i++) {
                                Class componentClass = clazz.getComponentType();
                                if (componentClass == String.class) {
                                    Array.set(valueArray, i, valueStringArray[i]);
                                } else {
                                    Array.set(valueArray, i, componentClass.getMethod("valueOf", String.class).invoke(null, valueStringArray[i]));
                                }
                            }
                            value = valueArray;
                        }
                        else {
                            value = clazz.getMethod("valueOf", String.class).invoke(null, valueString);
                        }

                        result.setFlagValue(flag, value);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                        throw new RuntimeException();
                    }
                }
        );

        return result;
    }
}
