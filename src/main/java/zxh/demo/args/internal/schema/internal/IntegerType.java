package zxh.demo.args.internal.schema.internal;

import zxh.demo.args.internal.schema.SchemaType;
import zxh.demo.args.internal.schema.internal.exception.InvalidValueException;

/**
 * IntegerType:
 * @author zhangxuhai
 * @date 2019-07-18
*/
public class IntegerType implements SchemaType<Integer> {

    private static IntegerType self = new IntegerType();

    private IntegerType() {
    }

    public static IntegerType getInstance() {
        return self;
    }

    @Override
    public String getName() {
        return "integer";
    }

    @Override
    public Integer valueOf(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new InvalidValueException();
        }
    }

    @Override
    public Integer getDefault() {
        return 0;
    }
}
