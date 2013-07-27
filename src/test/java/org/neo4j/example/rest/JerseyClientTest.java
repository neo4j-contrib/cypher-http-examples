package org.neo4j.example.rest;

import org.junit.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class JerseyClientTest {

    public static final int ID = 1;

    @Test
    public void testQuery() throws Exception {
        JerseyClient client = new JerseyClient("http://localhost:7474/");
        CypherClient.ExecutionResult result = client.query("start n=node({id}) return id(n) as id", Collections.<String, Object>singletonMap("id", ID));

        assertEquals(asList("id"),result.getColumns());

        assertEquals(ID, result.iterator().next().get(0));

        Iterator<Map<String,Object>> rowIterator = result.rowIterator();
        assertEquals(true, rowIterator.hasNext());
        Map<String, Object> row = rowIterator.next();
        assertEquals(1, row.size());
        assertEquals(true, row.containsKey("id"));
        assertEquals(ID, row.get("id"));
    }
}
