package org.neo4j.example.rest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.Map;

import static org.neo4j.example.rest.Util.*;

public class JerseyClient implements CypherClient {
    private final WebResource resource;

    public JerseyClient(String baseUri) {
        resource = Client.create().resource(toCypherUri(baseUri));

    }

    public ExecutionResult query(String statement, Map<String, Object> params) {
        String json = toJson(createPostData(statement, params));
        ClientResponse response =
                resource.accept(MediaType.APPLICATION_JSON_TYPE)
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .header("X-Stream","true")
                        .post(ClientResponse.class, json);
        String result = response.getEntity(String.class);
        response.close();
        int status = response.getStatus();
        System.out.printf("POST %s %nstatus code [%d] %nresult %s%n", json, status, result);
        return toResult(status,result);
    }

}
