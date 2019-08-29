package zxh.demo.args.internal.type;

/**
 * EmptySchemaType:
 * @author zhangxuhai
 * @date 2019-08-29
*/
public class EmptySchemaType implements SchemaType<Object> {
    @Override
    public Object valueOf(String value) {
        return null;
    }

    @Override
    public Object getDefault() {
        return null;
    }
}
