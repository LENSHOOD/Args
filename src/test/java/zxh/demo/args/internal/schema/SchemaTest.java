package zxh.demo.args.internal.schema;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import zxh.demo.args.internal.schema.type.StringSchemaType;

import static org.junit.Assert.assertEquals;

public class SchemaTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validate_build_string_schema() {
        assertEquals(StringSchemaType.getInstance(), new Schema("s:string").get("s"));
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
    public void validate_all_schema_default() {
        Schema schema = new Schema("s:string");
        assertEquals("", schema.getFlagDefaults().get("s"));
    }
}