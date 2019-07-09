package zxh.demo.args.internal;

import zxh.demo.args.internal.exception.BuildSchemaException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Schema:
 * @author zhangxuhai
 * @date 2019-07-09
*/
public class Schema {
    private enum SchemaType {
        /**
         * Pre-defined schema type
         */
        BOOLEAN("boolean", Boolean.class, Boolean.FALSE),
        INTEGER("integer", Integer.class, 0),
        DOUBLE("double", Double.class, 0.0),
        STRING("string", String.class, ""),
        INTEGER_ARRAY("integer-array", Integer[].class, new Integer[]{}),
        DOUBLE_ARRAY("double-array", Double[].class, new Double[]{}),
        STRING_ARRAY("string-array", String[].class, new String[]{});

        private String name;
        private Class clazz;
        private Object defaultValue;

        SchemaType(String name, Class clazz, Object defaultValue) {
            this.name = name;
            this.clazz = clazz;
            this.defaultValue = defaultValue;
        }

        static SchemaType findSchemaTypeBy(String givenTypeName) {
            return Stream.of(SchemaType.values())
                    .filter(schemaType ->  givenTypeName.equals(schemaType.name))
                    .findFirst().orElse(null);
        }
    }

    Map<String, SchemaType> schemaMap = new HashMap<>();

    public Schema(String schemaString) {
        schemaString = schemaString.trim();
        String[] flagTypePairs = schemaString.split(",");
        if (Stream.of(flagTypePairs).allMatch(String::isEmpty)) {
            throw new BuildSchemaException("Invalid schema string, no flag type pair found!");
        }

        for (String flagTypePair : flagTypePairs) {
            String[] flagTypePairArray = flagTypePair.split(":");

            if (flagTypePairArray.length != 2) {
                throw new BuildSchemaException("Invalid flag type pair: " + flagTypePair);
            }

            String flag = flagTypePairArray[0].trim();
            String type = flagTypePairArray[1].trim();
            SchemaType schemaType = SchemaType.findSchemaTypeBy(type);

            if (Objects.isNull(schemaType)) {
                throw new BuildSchemaException("Invalid schema type: " + flagTypePair);
            }
            schemaMap.put(flag, schemaType);
        }
    }

    public Class getClassBy(String flag) {
        if (Objects.isNull(flag)) {
            return null;
        }

        SchemaType resultType = schemaMap.get(flag);

        if (Objects.isNull(resultType)) {
            return null;
        }

        return resultType.clazz;
    }

    public Map<String, Object> getAllFlagWithDefaultValue() {
        Map<String, Object> resultMap = new HashMap<>();
        schemaMap.forEach((k, v) -> resultMap.put(k, v.defaultValue));
        return resultMap;
    }
}
