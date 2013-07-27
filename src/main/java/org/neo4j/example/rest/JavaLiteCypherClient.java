package org.neo4j.example.rest;

import org.javalite.http.Http;
import org.javalite.http.Post;

import java.util.Map;

import static org.neo4j.example.rest.Util.*;

public class JavaLiteCypherClient implements CypherClient {
    private String uri;

    public JavaLiteCypherClient(String baseUri) {
        this.uri = Util.toCypherUri(baseUri);
    }

    @Override
    public ExecutionResult query(String statement, Map<String, Object> params) {
        String content = toJson(Util.createPostData(statement, params));
        Post post = Http.post(uri, content).header("accept","application/json").header("content-type","application/json").header("X-Stream","true");
        return Util.toResult(post.responseCode(),post.text());
    }
}
