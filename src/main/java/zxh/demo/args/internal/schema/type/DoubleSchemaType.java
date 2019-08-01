package zxh.demo.args.internal.schema.type;

/**
 * DoubleSchemaType:
 * @author zhangxuhai
 * @date 2019-08-01
*/
public class DoubleSchemaType implements SchemaType<Double> {

    private static final DoubleSchemaType SELF = new DoubleSchemaType();
    private static final Double DEFAULT = 0.0;

    public static DoubleSchemaType getInstance() {
        return SELF;
    }

    @Override
    public Double parse(String input) {
        try {
            return Double.parseDouble(input);
        } catch (Exception e) {
            throw new SchemaTypeException(String.format("Get schema type error: %s", e.getMessage()));
        }
    }

    @Override
    public Double getDefault() {
        return DEFAULT;
    }
}
