package org.neo4j.example.rest;

import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.specimpl.BuiltResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class RestEasyClient implements CypherClient {
    private final String uri;

    public RestEasyClient(String baseUri) {
        this.uri = Util.toCypherUri(baseUri);
    }

    @Override
    public ExecutionResult query(String statement, Map<String, Object> params) {
        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target(uri);
        BuiltResponse response = (BuiltResponse) target.request().accept(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(Util.toJson(Util.createPostData(statement, params))));
        int status = response.getStatus();
        String result = response.readEntity(String.class);
        return Util.toResult(status,result);
    }
}
