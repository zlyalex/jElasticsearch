jElasticsearch
==============

jElasticsearch is a object-oriented Elasticsearch high-level client libraries collection for Java/JVM.


Connectivity (clients)
--------------

jElasticsearch provides predefined ways to work with Elasticsearch:
- **ElasticsearchTransport** - transport client, does not join the cluster, have less dependencies than other
- **ElasticsearchNode** - node client, joins the cluster and works as a node (with data storage or not)
- **ElasticsearchEmbedded** - node with local (JVM class loader) discovery and transport

Every type of ES connectivity has its own Maven artifact to maintain dependencies properly.

Use
```Java
import com.sproutigy.commons.jelasticsearch.client.*;
ElasticsearchEmbedded es = new ElasticsearchEmbedded();
es.setPersistent(false); //keep documents in memory, do not store them
```

or:
```Java
import com.sproutigy.commons.jelasticsearch.client.*;
ElasticsearchTransport es = new ElasticsearchTransport();
es.setClusterName("myservice");
es.addTransportAddress("esindex.myservice.com");
```

and then:
```Java
es.connect(); //optional (but useful to catch problems sooner)
org.elasticsearch.Client client = es.client();

//now use client as Elasticsearch Java API documentation says, for example:
CreateIndexRequestBuilder request = client.admin().indices().prepareCreate("testindex");
CreateIndexResponse response = request.execute().actionGet();

es.close(); //close connection and release resources
```

It simplifies Elasticsearch Java client API by providing connectors objects (all implements same ElasticsearchClientProvider interface) with methods to set up clients (instead of builders and map-based settings). What is more, ElasticSearchTransportClient Maven POM excludes all Lucene dependencies (which are not needed in this case) so that your final build will be lighter than using standard ES dependency.

More about Elasticsearch Java API:
http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/index.html


TODO
--------------
jElasticsearch will soon provide object-oriented JSON builders for search queries and mappings.


Maven
--------------

Currently supported Elasticsearch version is: **1.1.1**
```XML
    <properties>
        <elasticsearch.version>1.1.1</elasticsearch.version>
    </properties>
```

- ElasticsearchTransport:
```XML
<dependency>
    <groupId>com.sproutigy.commons.jelasticsearch</groupId>
    <artifactId>jelasticsearch-client-transport</artifactId>
    <version>${elasticsearch.version}</version>
</dependency>
```

- ElasticsearchNode:
```XML
<dependency>
    <groupId>com.sproutigy.commons.jelasticsearch</groupId>
    <artifactId>jelasticsearch-client-node</artifactId>
    <version>${elasticsearch.version}</version>
</dependency>
```

- ElasticsearchEmbedded:
```XML
<dependency>
    <groupId>com.sproutigy.commons.jelasticsearch</groupId>
    <artifactId>jelasticsearch-client-embedded</artifactId>
    <version>${elasticsearch.version}</version>
</dependency>
```
