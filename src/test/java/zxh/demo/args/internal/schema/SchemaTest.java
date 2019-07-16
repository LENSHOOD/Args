package zxh.demo.args.internal.schema;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import zxh.demo.args.internal.exception.BuildSchemaException;
import zxh.demo.args.internal.schema.internal.StringType;

import static org.junit.Assert.assertEquals;

public class SchemaTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validate_stringSchema() {
        String schemaString = "s:string";
        assertEquals(StringType.getInstance(), new Schema(schemaString).getTypeByFlag("s"));
    }

    @Test
    public void validate_wrongSchemaString() {
        String wrongSchemaString = "sstring";
        expectedException.expect(BuildSchemaException.class);
        expectedException.expectMessage("Invalid schema string: sstring");
        new Schema(wrongSchemaString);
    }

    @Test
    public void validate_emptySchema() {
        String emptyString = ":string";
        expectedException.expect(BuildSchemaException.class);
        expectedException.expectMessage("Invalid schema string: :string, has empty!");
        new Schema(emptyString);
    }

    @Test
    public void validate_wrongSchemaType() {
        String wrongTypeSchema = "s:schema";
        expectedException.expect(BuildSchemaException.class);
        expectedException.expectMessage("Wrong type schema for: s:schema");
        new Schema(wrongTypeSchema);
    }
}