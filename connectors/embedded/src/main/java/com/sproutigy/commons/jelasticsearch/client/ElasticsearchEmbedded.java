package com.sproutigy.commons.jelasticsearch.client;

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

    @Override
    protected void buildNode(NodeBuilder nodeBuilder) {
        super.buildNode(nodeBuilder);
        nodeBuilder.local(true);
    }
}
