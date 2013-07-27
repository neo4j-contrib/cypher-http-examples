package org.neo4j.example.rest;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;

import java.io.IOException;
import java.util.Map;

public class RestletClient implements CypherClient {
    String uri;

    public RestletClient(String baseUri) {
        this.uri = Util.toCypherUri(baseUri);
    }

    @Override
    public ExecutionResult query(String statement, Map<String, Object> params) {
        try {
            ClientResource resource = new ClientResource(uri);
            Representation response = resource.post(new StringRepresentation(Util.toJson(Util.createPostData(statement, params)), MediaType.APPLICATION_JSON));
            Status status = resource.getStatus();
            String result = response.getText();
            return Util.toResult(status.getCode(),result);
        } catch (IOException e) {
            throw new RuntimeException("Error executing request ",e);
        }
    }
}
