package org.neo4j.example.rest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CypherClientTest {

    private static int NODE_ID;
    private static LocalTestServer server;
    private final CypherClient client;

    @BeforeClass
    public static void beforeClass() {
        server = new LocalTestServer();
        server.start();
        NODE_ID = (int) createNode();
    }

    private static long createNode() {
        GraphDatabaseService db = server.getGraphDatabase();
        Transaction tx = db.beginTx();
        long id=db.createNode().getId();
        tx.success();
        tx.finish();
        return id;
    }

    @AfterClass
    public static void afterClass() {
        server.stop();
    }

    public CypherClientTest(Clients clients) {
        client = clients.create(server.getBaseUrl());
    }

    enum Clients {
        JERSEY() { public CypherClient create(String url) { return new JerseyClient(url); } },
        JAVA_LITE() { public CypherClient create(String url) { return new JavaLiteCypherClient(url); } };


        public abstract CypherClient create(String url);
    }

    @Parameterized.Parameters(name = "Client: {0}")
    public static Iterable<Object[]> parameters() {
        return Arrays.asList(new Object[][]{{Clients.JERSEY}, {Clients.JAVA_LITE}});
    }

    @Test
    public void testQuery() throws Exception {
        ExecutionResult result = client.query("start n=node({id}) return id(n) as id", Collections.<String, Object>singletonMap("id", NODE_ID));

        assertEquals(asList("id"),result.getColumns());

        assertEquals(NODE_ID, result.iterator().next().get(0));

        Iterator<Map<String,Object>> rowIterator = result.rowIterator();
        assertEquals(true, rowIterator.hasNext());
        Map<String, Object> row = rowIterator.next();
        assertEquals(1, row.size());
        assertEquals(true, row.containsKey("id"));
        assertEquals(NODE_ID, row.get("id"));
    }
}
