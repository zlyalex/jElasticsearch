package com.sproutigy.commons.jelasticsearch.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import java.util.HashMap;
import java.util.UUID;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * Node client, joins the cluster (fully aware of its infrastructure) and works as a non-data node
 *
 * @author Lucas Nawojczyk (LukeAheadNET) < dev@lukeahead.net >
 * @since 2014-04-29
 */
public class ElasticsearchNode implements ElasticsearchConnector, AutoCloseable {

    private Node node;

    public Node getNode() {
        return node;
    }


    private String clusterName;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setRandomClusterName() {
        setClusterName("elasticsearch-" + UUID.randomUUID().toString());
    }


    private boolean httpEnabled = false;

    public boolean isHttpEnabled() {
        return httpEnabled;
    }

    public void setHttpEnabled(boolean httpEnabled) {
        this.httpEnabled = httpEnabled;
    }


    private HashMap<String, Object> manualSetting = new HashMap<String, Object>();

    public void setManualSetting(String key, Object value) {
        manualSetting.put(key, value);
    }

    public Object getManualSetting(String key) {
        return manualSetting.get(key);
    }


    @Override
    public void connect() {
        NodeBuilder nodeBuilder = nodeBuilder();
        buildNode(nodeBuilder);

        //settings
        ImmutableSettings.Builder settingsBuilder = ImmutableSettings.settingsBuilder();
        buildSettings(settingsBuilder);
        for (String key : manualSetting.keySet()) {
            settingsBuilder.put(key, manualSetting.get(key));
        }
        nodeBuilder.settings(settingsBuilder);

        node = nodeBuilder.node();
    }

    public Client client() {
        if (node == null)
            connect();
        return node.client();
    }

    @Override
    public boolean isConnected() {
        return node != null;
    }

    protected void buildNode(NodeBuilder nodeBuilder) {
        if (clusterName != null)
            nodeBuilder.clusterName(clusterName);
    }

    protected void buildSettings(ImmutableSettings.Builder settingsBuilder) {
        settingsBuilder.put("node.data", false);
        settingsBuilder.put("http.enabled", isHttpEnabled());
    }

    @Override
    public void close() {
        if (node != null) {
            node.close();
            node = null;
        }
    }
}
