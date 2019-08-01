package zxh.demo.args.internal.schema.type;

/**
 * IntegerSchemaType:
 *
 * @author zhangxuhai
 * @date 2019-08-01
 */
public class IntegerSchemaType implements SchemaType<Integer> {

    private static final IntegerSchemaType SELF = new IntegerSchemaType();
    private static final Integer DEFAULT = 0;

    public static IntegerSchemaType getInstance() {
        return SELF;
    }

    @Override
    public Integer parse(String input) {
        try {
            return Integer.parseInt(input);
        } catch (Exception e) {
            throw new SchemaTypeException(String.format("Get schema type error: %s", e.getMessage()));
        }
    }

    @Override
    public Integer getDefault() {
        return DEFAULT;
    }
}
