package org.neo4j.example.rest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface ExecutionResult extends Iterable<List<Object>> {
    List<String> getColumns();

    Iterator<List<Object>> iterator();

    Iterator<Map<String,Object>> rowIterator();
}
