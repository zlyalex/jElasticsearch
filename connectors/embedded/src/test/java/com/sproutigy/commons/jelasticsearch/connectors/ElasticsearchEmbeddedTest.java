package com.sproutigy.commons.jelasticsearch.connectors;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Lucas Nawojczyk (LukeAheadNET) < dev@lukeahead.net >
 * @since 2014-04-30
 */
public class ElasticsearchEmbeddedTest {

    @Test
    public void testStartStop() {
        ElasticsearchEmbedded es = new ElasticsearchEmbedded();
        assertThat(es.isConnected(), is(false));
        es.connect();
        assertThat(es.isConnected(), is(true));
        assertThat(es.client(), notNullValue());
        es.close();
        assertThat(es.isConnected(), is(false));
    }

    @Test
    public void testIndexAdministration() {
        ElasticsearchEmbedded es = new ElasticsearchEmbedded();
        es.setPersistent(false);

        String indexName = "embeddedtestindex";

        CreateIndexRequestBuilder createIndexRequestBuilder = es.client().admin().indices().prepareCreate(indexName);
        CreateIndexResponse createIndexResponse = createIndexRequestBuilder.execute().actionGet();
        assertTrue(createIndexResponse.isAcknowledged());

        DeleteIndexRequestBuilder requestBuilder = es.client().admin().indices().prepareDelete(indexName);
        DeleteIndexResponse deleteIndexResponse = requestBuilder.execute().actionGet();
        assertTrue(deleteIndexResponse.isAcknowledged());
    }

}
