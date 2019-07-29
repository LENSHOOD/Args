package zxh.demo.args.internal.schema;

import zxh.demo.args.internal.schema.type.StringSchemaType;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Schema:
 * @author zhangxuhai
 * @date 2019-07-28
*/
public class Schema {

    private static final Map<String, StringSchemaType> TYPE_MAP = new HashMap<>();
    static {
        TYPE_MAP.put("string", StringSchemaType.getInstance());
    }

    private Map<String, StringSchemaType> flagTypeMap = new HashMap<>();

    class SchemaException extends RuntimeException {
        SchemaException(String message) {
            super(message);
        }
    }

    public Schema(String schemaString) {
        build(schemaString);
    }

    private void build(String schemaString) {
        String[] flagAndTypeStrings = schemaString.split(",");
        Stream.of(flagAndTypeStrings).forEach(element -> {
            String[] flagAndTypeString = element.split(":");
            if (flagAndTypeString.length != 2) {
                throw new SchemaException(String.format("Build schema error, wrong flag type pair: %s", element));
            }

            String flag = flagAndTypeString[0];
            StringSchemaType type = TYPE_MAP.get(flagAndTypeString[1]);

            if (type == null) {
                return;
            }

            flagTypeMap.put(flag, type);
        });
    }

    public StringSchemaType get(String flag) {
        if (flag == null) {
            throw new SchemaException("Get schema type error: null");
        }

        return flagTypeMap.get(flag);
    }

    public Map<String, Object> getFlagDefaults() {
        Map<String, Object> resultMap = new HashMap<>();
        flagTypeMap.forEach((k, v) -> resultMap.put(k, v.getDefault()));
        return resultMap;
    }
}
