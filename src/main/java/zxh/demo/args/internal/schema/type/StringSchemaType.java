package zxh.demo.args.internal.schema.type;

/**
 * StringSchemaType:
 * @author zhangxuhai
 * @date 2019-07-28
*/
public class StringSchemaType {

    private static final String DEFAULT = "";

    private static final StringSchemaType SELF = new StringSchemaType();

    private StringSchemaType() {
        // empty
    }

    public static StringSchemaType getInstance() {
        return SELF;
    }

    public String parse(String input) {
        return input;
    }

    public String getDefault() {
        return DEFAULT;
    }
}
