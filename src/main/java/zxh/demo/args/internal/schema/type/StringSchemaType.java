package zxh.demo.args.internal.schema.type;

/**
 * StringSchemaType:
 * @author zhangxuhai
 * @date 2019-07-28
*/
public class StringSchemaType implements SchemaType<String> {

    private static final String DEFAULT = "";

    private static final StringSchemaType SELF = new StringSchemaType();

    private StringSchemaType() {
        // empty
    }

    public static StringSchemaType getInstance() {
        return SELF;
    }

    @Override
    public String parse(String input) {
        return input;
    }

    @Override
    public String getDefault() {
        return DEFAULT;
    }
}
