package zxh.demo.args.internal.type;

import zxh.demo.args.internal.exception.SchemaException;

/**
 * DoubleSchemaType:
 * @author zhangxuhai
 * @date 2019-08-30
*/
public class DoubleSchemaType implements SchemaType<Double> {
    @Override
    public Double valueOf(String value) {
        if ("".equals(value)) {
            value = "null";
        }

        try {
            return Double.valueOf(value);
        } catch (Exception e) {
            throw new SchemaException(String.format("Invalid input value: %s", value));
        }
    }

    @Override
    public Double getDefault() {
        return 0D;
    }
}
