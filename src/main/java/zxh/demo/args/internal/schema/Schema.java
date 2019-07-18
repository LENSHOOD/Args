package zxh.demo.args.internal.schema;

import zxh.demo.args.internal.schema.internal.BooleanType;
import zxh.demo.args.internal.schema.internal.DoubleType;
import zxh.demo.args.internal.schema.internal.IntegerType;
import zxh.demo.args.internal.schema.internal.StringType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Schema:
 * @author zhangxuhai
 * @date 2019-07-16
*/
public class Schema {

    private enum SchemaTypeEnum {
        /**
         * Schema Type Enum
         */
        STRING(StringType.getInstance()),
        BOOLEAN(BooleanType.getInstance()),
        INTEGER(IntegerType.getInstance()),
        DOUBLE(DoubleType.getInstance());

        private SchemaType type;

        SchemaTypeEnum(SchemaType type) {
            this.type = type;
        }

        private static SchemaTypeEnum getTypeByString(String type) {
            return Stream.of(SchemaTypeEnum.values())
                    .filter(typeElement -> typeElement.type.getName().equalsIgnoreCase(type))
                    .findFirst()
                    .orElse(null);
        }
    }

    private Map<String, SchemaTypeEnum> flagTypeMap = new HashMap<>();

    public Schema(String schemaString) {
        buildSchema(schemaString);
    }

    public SchemaType getTypeByFlag(String flag) {
        if (Objects.isNull(flag) || !flagTypeMap.containsKey(flag)) {
            return null;
        }

        return flagTypeMap.get(flag).type;
    }

    public Map<String, Object> getDefaultSchemaMap() {
        Map<String, Object> defaultSchemaMap = new HashMap<>();
        flagTypeMap.forEach((k,v) -> defaultSchemaMap.put(k, v.type.getDefault()));
        return defaultSchemaMap;
    }

    private void buildSchema(String schemaString) {
        String[] flagTypePairs = schemaString.split(",");
        Stream.of(flagTypePairs).forEach(
                flagTypePair -> {
                    String[] flagAndType = flagTypePair.split(":");
                    if (flagAndType.length != 2) {
                        throw new BuildSchemaException("Invalid schema string: " + flagTypePair);
                    }

                    String flag = flagAndType[0].trim();
                    String type = flagAndType[1].trim();

                    if (isEmpty(flag) || isEmpty(type)) {
                        throw new BuildSchemaException(String.format("Invalid schema string: %s, has empty!", flagTypePair));
                    }

                    SchemaTypeEnum schemaType = SchemaTypeEnum.getTypeByString(type);
                    if (Objects.isNull(schemaType)) {
                        throw new BuildSchemaException(String.format("Wrong type %s for: %s", type, flagTypePair));
                    }

                    flagTypeMap.put(flag, schemaType);
                }
        );
    }

    private boolean isEmpty(String s) {
        return Objects.isNull(s) || s.isEmpty();
    }
}
