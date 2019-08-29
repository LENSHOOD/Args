package zxh.demo.args.internal.type;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import zxh.demo.args.internal.exception.SchemaException;

import static org.junit.Assert.*;

public class SchemaTypeTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validate_string_type() {
        SchemaType schemaType = new StringSchemaType();
        assertEquals("test string", schemaType.valueOf("test string"));
        assertEquals("", schemaType.getDefault());
    }

    @Test
    public void validate_boolean_type() {
        SchemaType schemaType = new BooleanSchemaType();
        assertEquals(Boolean.TRUE, schemaType.valueOf("true"));
        assertEquals(Boolean.TRUE, schemaType.valueOf(""));
        assertEquals(Boolean.FALSE, schemaType.getDefault());
    }

    @Test
    public void validate_boolean_wrong_input() {
        SchemaType schemaType = new BooleanSchemaType();
        expectedException.expect(SchemaException.class);
        expectedException.expectMessage("Invalid input value: yes");
        assertEquals(Boolean.TRUE, schemaType.valueOf("yes"));
    }
}