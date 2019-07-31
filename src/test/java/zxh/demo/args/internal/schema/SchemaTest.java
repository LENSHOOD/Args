package zxh.demo.args.internal.schema;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import zxh.demo.args.internal.schema.type.BooleanSchemaType;
import zxh.demo.args.internal.schema.type.StringSchemaType;

import static org.junit.Assert.*;

public class SchemaTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validate_build_schema() {
        assertEquals(StringSchemaType.getInstance(), new Schema("s:string").get("s"));
        assertEquals(BooleanSchemaType.getInstance(), new Schema("b:boolean").get("b"));
    }

    @Test
    public void validate_not_take_null() {
        expectedException.expect(Schema.SchemaException.class);
        expectedException.expectMessage("Get schema type error: null");
        new Schema("s:string").get(null);
    }

    @Test
    public void validate_string_schema_parse() {
        Schema schema = new Schema("s:string");
        assertEquals("iamstring", schema.get("s").parse("iamstring"));
    }

    @Test
    public void validate_string_schema_default() {
        Schema schema = new Schema("s:string");
        assertEquals("", schema.get("s").getDefault());
    }

    @Test
    public void validate_boolean_schema_parse() {
        Schema schema = new Schema("b:boolean");
        assertTrue((Boolean) schema.get("b").parse(""));
    }

    @Test
    public void validate_boolean_schema_default() {
        Schema schema = new Schema("b:boolean");
        assertFalse((Boolean) schema.get("b").getDefault());
    }

    @Test
    public void validate_wrong_boolean_schema() {
        Schema schema = new Schema("b:boolean");
        expectedException.expect(Schema.SchemaException.class);
        expectedException.expectMessage("Get schema type error: yes");
        schema.get("b").parse("yes");
    }

    @Test
    public void validate_all_schema_default() {
        Schema schema = new Schema("s:string");
        assertEquals("", schema.getFlagDefaults().get("s"));
    }
}