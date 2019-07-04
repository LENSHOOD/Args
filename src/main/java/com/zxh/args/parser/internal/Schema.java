package com.zxh.args.parser.internal;

import com.zxh.args.parser.internal.exception.BuildSchemaException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Schema:
 * @author zhangxuhai
 * @date 2019-06-30
*/
public class Schema {

    private enum SchemaType {
        /**
         * Type of flags
         */
        BOOLEAN("boolean", Boolean.class, Boolean.FALSE),
        INTEGER("integer", Integer.class, 0),
        INTEGER_ARRAY("integer-array", Integer[].class, new Integer[]{}),
        DOUBLE("double", Double.class, 0.0),
        DOUBLE_ARRAY("double-array", Double[].class, new Double[]{}),
        STRING("string", String.class, ""),
        STRING_ARRAY("string-array", String[].class, new String[]{});

        private String typeName;
        private Class clazz;
        private Object defaultValue;

        SchemaType(String typeName, Class clazz, Object defaultValue) {
            this.typeName = typeName;
            this.clazz = clazz;
            this.defaultValue = defaultValue;
        }

        private static SchemaType getSchemaTypeByName(String typeName) {
             return Stream.of(
                     SchemaType.values())
                     .filter(schemaType -> schemaType.typeName.equalsIgnoreCase(typeName))
                     .findFirst()
                     .orElseThrow(() -> new BuildSchemaException("Wrong flag:" + typeName));
        }
    }

    private Map<String, SchemaType> schemaMap = new HashMap<>();

    public Schema(String yamlFileAddr) {
        try (FileInputStream yamlIs = new FileInputStream(new File(yamlFileAddr))) {
            Yaml yaml = new Yaml();
            Map<String, String> yamlMap = yaml.load(yamlIs);
            yamlMap.forEach((k, v) -> schemaMap.put(k, SchemaType.getSchemaTypeByName(v)));
        } catch (IOException e) {
            throw new BuildSchemaException(e.getMessage());
        }
    }

    public Class getFlagClass(String flagName) {
        SchemaType schemaType = schemaMap.get(flagName);
        return Objects.isNull(schemaType) ? null : schemaType.clazz;
    }

    public Map<String, Object> getFlagWithDefaultValue() {
        Map<String, Object> defaultValueMap = new HashMap<>();
        schemaMap.forEach((k, v) -> defaultValueMap.put(k, v.defaultValue));
        return defaultValueMap;
    }
}
