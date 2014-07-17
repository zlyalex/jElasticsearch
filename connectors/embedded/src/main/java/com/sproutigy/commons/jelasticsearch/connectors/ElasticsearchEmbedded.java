package com.sproutigy.commons.jelasticsearch.connectors;

import org.elasticsearch.node.NodeBuilder;

/**
 * Data node with local (JVM class loader) discovery and transport
 *
 * @author Lucas Nawojczyk (LukeAheadNET) < dev@lukeahead.net >
 * @since 2014-05-12
 */
public class ElasticsearchEmbedded extends ElasticsearchDataNode {
    public ElasticsearchEmbedded() {
        super();
    }

    public ElasticsearchEmbedded(boolean persistent) {
        super(persistent);
    }

    public ElasticsearchEmbedded(boolean persistent, boolean local) {
        super(persistent);
        this.local = local;
    }

    private boolean local = true;

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    @Override
    protected void buildNode(NodeBuilder nodeBuilder) {
        super.buildNode(nodeBuilder);
        nodeBuilder.local(isLocal());
    }
}
