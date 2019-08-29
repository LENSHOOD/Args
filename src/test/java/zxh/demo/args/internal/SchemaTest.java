package zxh.demo.args.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import zxh.demo.args.internal.exception.SchemaException;

import static org.junit.Assert.*;

public class SchemaTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validate_new_schema() {
        String schemaString = "s:string";
        assertEquals("", new Schema(schemaString).getTypeByFlag("s").getDefault());
        assertEquals("test string", new Schema(schemaString).getTypeByFlag("s").valueOf("test string"));
    }

    @Test
    public void validate_new_schema_wrong_input_string() {
        String wrongSchemaString = "s,string";
        expectedException.expect(SchemaException.class);
        expectedException.expectMessage("Invalid input schema string: s,string");
        new Schema(wrongSchemaString);
    }

    @Test
    public void validate_new_schema_wrong_type() {
        String wrongSchemaString = "s:wrong_string";
        expectedException.expect(SchemaException.class);
        expectedException.expectMessage("Invalid input schema type: wrong_string");
        new Schema(wrongSchemaString);
    }
}