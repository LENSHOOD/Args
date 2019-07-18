package zxh.demo.args.internal.schema.internal;

import zxh.demo.args.internal.schema.SchemaType;
import zxh.demo.args.internal.schema.internal.exception.InvalidValueException;

/**
 * DoubleType:
 * @author zhangxuhai
 * @date 2019-07-18
*/
public class DoubleType implements SchemaType<Double> {
    private static DoubleType self = new DoubleType();

    private DoubleType() {
    }

    public static DoubleType getInstance() {
        return self;
    }

    @Override
    public String getName() {
        return "double";
    }

    @Override
    public Double valueOf(String value) {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            throw new InvalidValueException();
        }
    }

    @Override
    public Double getDefault() {
        return 0.0;
    }
}
