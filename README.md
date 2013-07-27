# cypher-http-examples

Examples on how to connect to the Cypher endpoints from Java with different http libraries

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

## Jersey

See: http://jersey.java.net/nonav/documentation/latest/user-guide.html#client

Uses `Client, WebResource and ClientResponse` to access the Cypher endpoint.

````xml
      <dependency>
          <groupId>com.sun.jersey</groupId>
          <artifactId>jersey-client</artifactId>
          <version>1.9</version>
      </dependency>
````

## JavaLite

See: http://igorpolevoy.blogspot.de/2011/01/java-rest-with-ease.html http://javalt.org/

## Restlet

See: http://restlet.org/

## RestEasy

See: http://docs.jboss.org/resteasy/docs/3.0.2.Final/userguide/html/RESTEasy_Client_Framework.html

## HttpClient

## RestAssured
