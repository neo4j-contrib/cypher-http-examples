package org.neo4j.example.rest;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.server.CommunityNeoServer;
import org.neo4j.server.WrappingNeoServer;
import org.neo4j.server.configuration.ServerConfigurator;
import org.neo4j.server.preflight.PreFlightTasks;
import org.neo4j.test.ImpermanentGraphDatabase;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.Collections;
import java.util.Map;

public class LocalTestServer {
    public static final int PORT = 7777;
    private CommunityNeoServer neoServer;
    private final ImpermanentGraphDatabase graphDatabase;

    public LocalTestServer() {
        graphDatabase = (ImpermanentGraphDatabase) new TestGraphDatabaseFactory().newImpermanentDatabase();
    }

    public void start() {
        if (neoServer!=null) throw new IllegalStateException("Server already running");
        neoServer = new WrappingNeoServer(graphDatabase) {
            protected int getWebServerPort() { return PORT; }
        };
        neoServer.start();
    }

    public void stop() {
        try {
            neoServer.stop();
        } catch(Exception e) {
            System.err.println("Error stopping server: "+e.getMessage());
        }
        neoServer=null;
    }


    public GraphDatabaseService getGraphDatabase() {
        return graphDatabase;
    }

    public void clean() {
        graphDatabase.cleanContent();
    }

    public String getBaseUrl() {
        return "http://localhost:"+PORT;
    }
}