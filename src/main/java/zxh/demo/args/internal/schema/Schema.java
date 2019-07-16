package zxh.demo.args.internal.schema;

import zxh.demo.args.internal.exception.BuildSchemaException;
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

    private enum SchemaType {
        /**
         * Schema Type Enum
         * elements:
         * typeName for name string,
         * typeClass for related class,
         * defaultValue for initial value
         */
        STRING(StringType.getInstance());

        private AbstractSchemaType type;

        SchemaType(AbstractSchemaType type) {
            this.type = type;
        }

        private static SchemaType getTypeByString(String type) {
            return Stream.of(SchemaType.values())
                    .filter(typeElement -> typeElement.type.getName().equalsIgnoreCase(type))
                    .findFirst()
                    .orElse(null);
        }
    }

    private Map<String, SchemaType> flagTypeMap = new HashMap<>();

    public Schema(String schemaString) {
        buildSchema(schemaString);
    }

    public AbstractSchemaType getTypeByFlag(String flag) {
        if (Objects.isNull(flag) || !flagTypeMap.containsKey(flag)) {
            return null;
        }

        return flagTypeMap.get(flag).type;
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

                    SchemaType schemaType = SchemaType.getTypeByString(type);
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
