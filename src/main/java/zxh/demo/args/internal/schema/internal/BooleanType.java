package zxh.demo.args.internal.schema.internal;

import zxh.demo.args.internal.schema.SchemaType;
import zxh.demo.args.internal.schema.internal.exception.InvalidValueException;

/**
 * BooleanType:
 * @author zhangxuhai
 * @date 2019-07-17
*/
public class BooleanType implements SchemaType<Boolean> {

    private static BooleanType self = new BooleanType();

    private BooleanType() {
    }

    public static BooleanType getInstance() {
        return self;
    }

    @Override
    public String getName() {
        return "boolean";
    }

    @Override
    public Boolean valueOf(String value) {
        if (value.isEmpty()) {
            return Boolean.TRUE;
        }

        if (!"true".equalsIgnoreCase(value) || !"false".equalsIgnoreCase(value)) {
            throw new InvalidValueException();
        }

        return Boolean.valueOf(value);
    }

    @Override
    public Boolean getDefault() {
        return Boolean.FALSE;
    }
}
