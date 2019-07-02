package com.zxh.args.parser.internal;

import com.zxh.args.parser.internal.exception.BuildSchemaException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class SchemaTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validate_new_schema() {
        Schema schema = new Schema(getClass().getClassLoader().getResource("test-schema-1.yml").getPath());
        assertEquals(Boolean.class, schema.getFlagClass("l"));
        assertEquals(Integer.class, schema.getFlagClass("p"));
        assertEquals(String.class, schema.getFlagClass("d"));
        assertEquals(String[].class, schema.getFlagClass("g"));
        assertNull(schema.getFlagClass("f"));
    }

    @Test
    public void validate_wrong_schema_path() {
        expectedException.expect(BuildSchemaException.class);
        new Schema("");
    }

    @Test
    public void validate_wrong_schema_file() {
        expectedException.expect(BuildSchemaException.class);
        new Schema(getClass().getClassLoader().getResource("test-schema-2.yml").getPath());
    }
}