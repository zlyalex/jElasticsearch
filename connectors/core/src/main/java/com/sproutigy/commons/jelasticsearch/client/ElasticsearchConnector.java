package com.sproutigy.commons.jelasticsearch.client;

import org.elasticsearch.client.Client;

/**
 * Common interface for Elasticsearch client implementations in jElasticsearch
 *
 * @author Lucas Nawojczyk (LukeAheadNET) < dev@lukeahead.net >
 * @since 2014-04-29
 */
public interface ElasticsearchConnector extends AutoCloseable {

    void connect();

    Client client();

    boolean isConnected();

}
