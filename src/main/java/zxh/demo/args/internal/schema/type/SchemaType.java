package zxh.demo.args.internal.schema.type;

/**
 * SchemaType:
 * @author zhangxuhai
 * @date 2019-07-31
*/
public abstract class SchemaType<T> {

    public abstract T parse(String input);

    public abstract T getDefault();
}
