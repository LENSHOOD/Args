package zxh.demo.args.internal.type;

import org.junit.Test;

import static org.junit.Assert.*;

public class SchemaTypeTest {

    @Test
    public void validate_string_type() {
        SchemaType schemaType = new StringSchemaType();
        assertEquals("test string", schemaType.valueOf("test string"));
        assertEquals("", schemaType.getDefault());
    }
}