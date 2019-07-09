package zxh.demo.args.internal;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import zxh.demo.args.internal.exception.BuildSchemaException;

import java.util.Map;

import static org.junit.Assert.*;

public class SchemaTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final String VALID_SCHEMA_STRING =
            "b:boolean,i:integer, d:double,s:string, q:integer-array,w:string-array,e : double-array";

    @Test
    public void validate_fully_schema() {
        Schema schema = new Schema(VALID_SCHEMA_STRING);
        assertEquals(Boolean.class, schema.getClassBy("b"));
        assertEquals(Integer.class, schema.getClassBy("i"));
        assertEquals(Double.class, schema.getClassBy("d"));
        assertEquals(String.class, schema.getClassBy("s"));
        assertEquals(Integer[].class, schema.getClassBy("q"));
        assertEquals(String[].class, schema.getClassBy("w"));
        assertEquals(Double[].class, schema.getClassBy("e"));
    }

    @Test
    public void validate_flag_not_found() {
        Schema schema = new Schema(VALID_SCHEMA_STRING);
        assertNull(schema.getClassBy("t"));
    }

    @Test
    public void validate_null_flag() {
        Schema schema = new Schema(VALID_SCHEMA_STRING);
        assertNull(schema.getClassBy(null));
    }

    @Test
    public void validate_default_value() {
        Schema schema = new Schema(VALID_SCHEMA_STRING);
        Map<String, Object> defaultValueMap = schema.getAllFlagWithDefaultValue();
        assertEquals(Boolean.FALSE, defaultValueMap.get("b"));
        assertEquals(0, defaultValueMap.get("i"));
        assertEquals(0.0, defaultValueMap.get("d"));
        assertEquals("", defaultValueMap.get("s"));
        assertArrayEquals(new Integer[]{}, (Integer[])defaultValueMap.get("q"));
        assertArrayEquals(new String[]{}, (String[])defaultValueMap.get("w"));
        assertArrayEquals(new Double[]{}, (Double[])defaultValueMap.get("e"));
    }

    @Test
    public void validate_empty_schema() {
        String emptySchema = " ";
        expectedException.expect(BuildSchemaException.class);
        expectedException.expectMessage("Invalid schema string, no flag type pair found!");
        new Schema(emptySchema);
    }

    @Test
    public void validate_wrong_schema() {
        String wrongSchema = "1234567";
        expectedException.expect(BuildSchemaException.class);
        expectedException.expectMessage("Invalid flag type pair: 1234567");
        new Schema(wrongSchema);
    }

    @Test
    public void validate_not_complete_schema() {
        String notCompleteSchema = "b:boolean, i:";
        expectedException.expect(BuildSchemaException.class);
        expectedException.expectMessage("Invalid flag type pair:  i:");
        new Schema(notCompleteSchema);
    }

    @Test
    public void validate_not_match_type() {
        String notMatchType = "b:bo, i:int";
        expectedException.expect(BuildSchemaException.class);
        expectedException.expectMessage("Invalid schema type: b:bo");
        new Schema(notMatchType);
    }
}