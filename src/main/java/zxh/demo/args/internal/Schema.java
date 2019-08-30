package zxh.demo.args.internal;

import zxh.demo.args.internal.exception.SchemaException;
import zxh.demo.args.internal.type.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Schema:
 * @author zhangxuhai
 * @date 2019-08-29
*/
public class Schema {
    private static Map<String, SchemaType> schemaMap = new HashMap<>(1);
    static {
        schemaMap.put("string", new StringSchemaType());
        schemaMap.put("boolean", new BooleanSchemaType());
        schemaMap.put("integer", new IntegerSchemaType());
    }

    private Map<String, SchemaType> flagTypeMap = new HashMap<>();

    public Schema(String schemaString) {
        String[] flagAndType = schemaString.trim().split(":");

        if (flagAndType.length != 2) {
            throw new SchemaException(String.format("Invalid input schema string: %s", schemaString));
        }

        flagTypeMap.put(flagAndType[0], getSchemaTypeBy(flagAndType[1]));
    }

    private SchemaType getSchemaTypeBy(String name) {
        if (!schemaMap.containsKey(name)) {
            throw new SchemaException(String.format("Invalid input schema type: %s", name));
        }

        return schemaMap.get(name);
    }

    public SchemaType getTypeByFlag(String flag) {
        if (!flagTypeMap.containsKey(flag)) {
            return new EmptySchemaType();
        }

        return flagTypeMap.get(flag);
    }

    public Map<String, Object> getFlagAndDefaultValues() {
        Map<String, Object> flagAndDefaultValues = new HashMap<>(flagTypeMap.size());
        flagTypeMap.forEach((k,v) -> flagAndDefaultValues.put(k, v.getDefault()));

        return flagAndDefaultValues;
    }
}
