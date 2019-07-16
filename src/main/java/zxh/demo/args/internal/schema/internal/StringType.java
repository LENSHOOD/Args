package zxh.demo.args.internal.schema.internal;

import zxh.demo.args.internal.schema.SchemaType;

/**
 * StringType:
 * @author zhangxuhai
 * @date 2019-07-16
*/
public class StringType implements SchemaType<String> {

    private static StringType self = new StringType();

    private StringType() {
    }

    public static StringType getInstance() {
        return self;
    }

    @Override
    public String getName() {
        return "string";
    }

    @Override
    public String valueOf(String value) {
        return value;
    }

    @Override
    public String getDefault() {
        return "";
    }
}
