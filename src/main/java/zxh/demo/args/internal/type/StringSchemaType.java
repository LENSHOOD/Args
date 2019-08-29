package zxh.demo.args.internal.type;

/**
 * StringSchemaType:
 * @author zhangxuhai
 * @date 2019-08-29
*/
public class StringSchemaType implements SchemaType<String> {

    @Override
    public String valueOf(String value) {
        return value;
    }

    @Override
    public String getDefault() {
        return "";
    }
}
