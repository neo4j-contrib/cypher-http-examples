# cypher-http-examples

Examples on how to connect to the Cypher endpoints from Java with different http libraries

**Contributions and Feedback wanted!!!**

## Infrastructure


There is a `CypherClient` interface with different implementations for different http libraries.

````java
public interface CypherClient {
    ExecutionResult query(String statement, Map<String,Object> params);
}
```

`ExecutionResult` is an interface with methods: 

````java
public interface ExecutionResult extends Iterable<List<Object>> {
    List<String> getColumns();

    Iterator<List<Object>> iterator();

    Iterator<Map<String,Object>> rowIterator();
}
````

A `Util` class takes care of URL handling, JSON conversion and postData and `ExecutionResult` instantiation.

## [Jersey Client](https://github.com/jexp/cypher-http-examples/blob/master/src/main/java/org/neo4j/example/rest/JerseyClient.java)

See: http://jersey.java.net/nonav/documentation/latest/user-guide.html#client

Uses `Client, WebResource and ClientResponse` to access the Cypher endpoint.

````xml
      <dependency>
          <groupId>com.sun.jersey</groupId>
          <artifactId>jersey-client</artifactId>
          <version>1.9</version>
      </dependency>
````

## [JavaLite](https://github.com/jexp/cypher-http-examples/blob/master/src/main/java/org/neo4j/example/rest/JavaLiteCypherClient.java)

See: http://igorpolevoy.blogspot.de/2011/01/java-rest-with-ease.html http://javalt.org/

## [Restlet](https://github.com/jexp/cypher-http-examples/blob/master/src/main/java/org/neo4j/example/rest/RestletClient.java)

See: http://restlet.org/

## [RestEasy](https://github.com/jexp/cypher-http-examples/blob/master/src/main/java/org/neo4j/example/rest/RestEasyClient.java)

See: http://docs.jboss.org/resteasy/docs/3.0.2.Final/userguide/html/RESTEasy_Client_Framework.html

## HttpClient

## RestAssured


# Todo

* Auth
* Neo4j 2.0 transactional Cypher endpoint examples (including transactions, rollback, batching and streaming)
* Streaming JSON back and forth
* .. more client libraries ..