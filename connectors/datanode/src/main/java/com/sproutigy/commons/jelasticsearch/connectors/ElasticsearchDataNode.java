package com.sproutigy.commons.jelasticsearch.connectors;

import org.elasticsearch.common.settings.ImmutableSettings;

/**
 * Node that joins the cluster and stores the data
 *
 * @author Lucas Nawojczyk (LukeAheadNET) < dev@lukeahead.net >
 * @since 2014-05-12
 */
public class ElasticsearchDataNode extends ElasticsearchNode {

    public ElasticsearchDataNode() {
        this(false);
    }

    public ElasticsearchDataNode(boolean persistent) {
        setPersistent(persistent);
    }


    private boolean persistent = true;

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }


    private String dataDirectory;

    public String getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }


    @Override
    protected void buildSettings(ImmutableSettings.Builder settingsBuilder) {
        super.buildSettings(settingsBuilder);

        settingsBuilder.put("node.data", true);

        if (!isPersistent()) {
            settingsBuilder.put("index.store.type", "memory");
        }

        if (getDataDirectory() != null) {
            settingsBuilder.put("path.data", getDataDirectory());
        }
    }
}
