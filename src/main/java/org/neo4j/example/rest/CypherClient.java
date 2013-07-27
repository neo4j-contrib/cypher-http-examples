package org.neo4j.example.rest;

import java.util.*;

/**
 * Created by mh on 26.07.13.
 */
public interface CypherClient {
    ExecutionResult query(String statement, Map<String,Object> params);

    public class ExecutionResult implements Iterable<List<Object>> {
        private final List<String> columns = new ArrayList<String>();
        private final List<List<Object>> data = new ArrayList<List<Object>>();
        private int columnCount;

        public ExecutionResult(List<String> columns) {
            this.columns.addAll(columns);
            this.columnCount = columns.size();
        }
        void addRow(List<Object> row) {
            data.add(row);
        }
        void addRows(List<List<Object>> rows) {
            this.data.addAll(rows);
        }

        public List<String> getColumns() {
            return columns;
        }

        @Override
        public Iterator<List<Object>> iterator() {
            return data.iterator();
        }

        public Iterator<Map<String,Object>> rowIterator() {
            return new RowIterator();
        }

        private class RowIterator implements Iterator<Map<String, Object>> {
            final Iterator<List<Object>> it;

            private RowIterator() {
                it = iterator();
            }

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public Map<String, Object> next() {
                List<Object> rowData = it.next();
                Map<String,Object> row = new LinkedHashMap<String, Object>(columnCount);
                for (int col = 0; col < columnCount; col++) {
                    row.put(columns.get(col),rowData.get(col));
                }
                return row;
            }

            @Override
            public void remove() {

            }
        }
    }
}
