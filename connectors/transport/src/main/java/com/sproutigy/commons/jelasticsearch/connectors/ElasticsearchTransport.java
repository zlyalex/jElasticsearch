package com.sproutigy.commons.jelasticsearch.connectors;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;

import java.util.ArrayList;

/**
 * Transport client, does not join the cluster and is not aware of infrastructure
 *
 * @author Lucas Nawojczyk (LukeAheadNET) < dev@lukeahead.net >
 * @since 2014-04-29
 */
public class ElasticsearchTransport implements ElasticsearchConnector, AutoCloseable {

    public static int DEFAULT_PORT = 9300;

    private ArrayList<TransportAddress> transportAddresses = new ArrayList<TransportAddress>();


    private String clusterName;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }


    private TransportClient client;


    public void addLocalTransportAddress() {
        addTransportAddress("localhost");
    }

    public void addLocalTransportAddress(int port) {
        addTransportAddress("localhost", port);
    }

    public void addTransportAddress(String host, int port) {
        transportAddresses.add(new InetSocketTransportAddress(host, port));
    }

    public void addTransportAddress(String host) {
        transportAddresses.add(new InetSocketTransportAddress(host, DEFAULT_PORT));
    }

    public void addTransportAddress(TransportAddress transportAddress) {
        transportAddresses.add(transportAddress);
    }


    @Override
    public void connect() {
        ImmutableSettings.Builder settingsBuilder = ImmutableSettings.settingsBuilder();
        if (getClusterName() != null) {
            settingsBuilder.put("cluster.name", getClusterName());
        }
        client = new TransportClient(settingsBuilder);
        for (TransportAddress transportAddress : transportAddresses) {
            client.addTransportAddress(transportAddress);
        }
    }

    public Client client() {
        if (client == null)
            connect();
        return client;
    }

    @Override
    public boolean isConnected() {
        return client != null;
    }

    @Override
    public void close() {
        if (client != null) {
            client.close();
            client = null;
        }
    }
}
