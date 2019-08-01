package zxh.demo.args.internal.schema.type;

/**
 * SchemaType:
 * @author zhangxuhai
 * @date 2019-07-31
*/
public interface SchemaType<T> {

    T parse(String input);

    T getDefault();
}
