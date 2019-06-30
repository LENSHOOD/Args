package com.zxh.args.parser.internal;

import com.zxh.args.parser.internal.exception.BuildSchemaException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Schema:
 * @author zhangxuhai
 * @date 2019-06-30
*/
public class Schema {

    enum SchemaType {
        /**
         * Type of flags
         */
        BOOLEAN("boolean", boolean.class),
        INTEGER("integer", int.class),
        INTEGER_ARRAY("integer-array", int[].class),
        DOUBLE("double", double.class),
        DOUBLE_ARRAY("double-array", double.class),
        STRING("string", String.class),
        STRING_ARRAY("string-array", String[].class);

        private String typeName;
        private Class clazz;

        SchemaType(String typeName, Class clazz) {
            this.typeName = typeName;
            this.clazz = clazz;
        }

        public static SchemaType getSchemaTypeByName(String typeName) {
             return Stream.of(
                     SchemaType.values())
                     .filter(schemaType -> schemaType.typeName.equalsIgnoreCase(typeName))
                     .findFirst()
                     .orElseThrow(() -> new BuildSchemaException("Wrong flag:" + typeName));
        }

        public Class getClazz() {
            return clazz;
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
        return schemaMap.get(flagName).getClazz();
    }
}
