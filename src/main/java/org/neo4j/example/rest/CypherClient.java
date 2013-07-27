package org.neo4j.example.rest;

import java.util.*;

public interface CypherClient {
    ExecutionResult query(String statement, Map<String,Object> params);
}
