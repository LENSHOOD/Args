package zxh.demo.args.internal.schema;

/**
 * AbstractSchemaType:
 * @author zhangxuhai
 * @date 2019-07-16
*/
public interface AbstractSchemaType<T> {

    /**
     * Type name
     */
    String getName();

    /**
     * Turn string value to value of type
     */
    T valueOf(String value);

    /**
     * Get default value of type
     */
    T getDefault();
}
