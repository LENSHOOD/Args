package zxh.demo.args.internal.type;

import zxh.demo.args.internal.exception.SchemaException;

/**
 * BooleanSchemaType:
 * @author zhangxuhai
 * @date 2019-08-29
*/
public class BooleanSchemaType implements SchemaType<Boolean> {
    @Override
    public Boolean valueOf(String value) {
        if ("".equalsIgnoreCase(value)) {
            return Boolean.TRUE;
        }

        if (!value.equalsIgnoreCase("true")
                && !value.equalsIgnoreCase("false")) {
            throw new SchemaException(String.format("Invalid input value: %s", value));
        }

        return Boolean.valueOf(value);
    }

    @Override
    public Boolean getDefault() {
        return Boolean.FALSE;
    }
}
