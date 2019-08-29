package zxh.demo.args.internal.type;

/**
 * SchemaType:
 * @author zhangxuhai
 * @date 2019-08-29
*/
public interface SchemaType<T> {

    /**
     * value of
     * @param value string value
     * @return value of type
     */
    T valueOf(String value);

    /**
     * default value with correlated type
     * @return default value
     */
    T getDefault();
}
