package zxh.demo.args;

import zxh.demo.args.exception.ParseException;
import zxh.demo.args.internal.Analyzer;
import zxh.demo.args.internal.schema.AbstractSchemaType;
import zxh.demo.args.internal.schema.Schema;

import java.util.Map;
import java.util.Objects;

/**
 * zxh.demo.args.Parser:
 * @author zhangxuhai
 * @date 2019-07-16
*/
public class Parser {

    private Schema schema;

    public void build(String schemaString) {
        schema = new Schema(schemaString);
    }

    public ParserResult parse(String inputArgs) {
        validateSchema();

        ParserResult result = new ParserResult();
        Map<String, String> flagValueMap = Analyzer.analyze(inputArgs);
        flagValueMap.forEach(
                (k,v) -> {
                    AbstractSchemaType type = schema.getTypeByFlag(k);

                    if (Objects.isNull(type)) {
                        throw new ParseException("Invalid flag: not found in schema!");
                    }

                    result.setFlagAndValue(k, type.valueOf(v));
                }
        );

        return result;
    }

    private void validateSchema() {
        if (Objects.isNull(schema)) {
            throw new ParseException("No build before parse!");
        }
    }
}
