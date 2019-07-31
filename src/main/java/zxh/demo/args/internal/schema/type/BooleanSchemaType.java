package zxh.demo.args.internal.schema.type;

/**
 * BooleanSchemaType:
 * @author zhangxuhai
 * @date 2019-07-31
*/
public class BooleanSchemaType extends SchemaType<Boolean> {

    private static final Boolean DEFAULT = Boolean.FALSE;
    private static final BooleanSchemaType SELF = new BooleanSchemaType();

    private BooleanSchemaType() {
        // empty
    }

    public static BooleanSchemaType getInstance() {
        return SELF;
    }

    @Override
    public Boolean parse(String input) {
        if ("".equals(input)) {
            return Boolean.TRUE;
        }

        if (!"true".equalsIgnoreCase(input) && !"false".equalsIgnoreCase(input)) {
            throw new RuntimeException("");
        }

        return Boolean.parseBoolean(input);
    }

    @Override
    public Boolean getDefault() {
        return DEFAULT;
    }
}
