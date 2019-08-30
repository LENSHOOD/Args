package zxh.demo.args.internal.type;

import zxh.demo.args.internal.exception.SchemaException;

/**
 * IntegerSchemaType:
 * @author zhangxuhai
 * @date 2019-08-30
*/
public class IntegerSchemaType implements SchemaType<Integer> {

    @Override
    public Integer valueOf(String value) {
        if ("".equals(value)) {
            value = "null";
        }

        try {
            return Integer.valueOf(value);
        } catch (Exception e) {
            throw new SchemaException(String.format("Invalid input value: %s", value));
        }
    }

    @Override
    public Integer getDefault() {
        return 0;
    }
}
