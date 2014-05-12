jElasticsearch
==============

jElasticsearch is a object-oriented Elasticsearch high-level client libraries collection for Java/JVM.


Connectivity
--------------

jElasticsearch simplifies Elasticsearch Java client API usage by providing predefined connectors (all implements common interface) with explicit methods to set them up (instead of builders and map-based settings). What is more, some Maven POMs excludes unused dependencies (e.g. Lucene libraries are not needed for ElasticsearchTransport and ElasticsearchNode) so that your final build will be lighter than using standard ES dependency.

jElasticsearch provides predefined ways to work with Elasticsearch cluster:
- **ElasticsearchTransport** - transport client, does not join the cluster and is not aware of infrastructure
- **ElasticsearchNode** - node client, joins the cluster (fully aware of its infrastructure) and works as a non-data node
- **ElasticsearchDataNode** - node that joins the cluster and stores the data
- **ElasticsearchEmbedded** - data node with local (JVM class loader) discovery and transport, self-running service

Every type of ES connectivity has its own Maven artifact to maintain dependencies properly.

For connectivity to external data servers, use ElasticsearchTransport or ElasticsearchNode client. Read more about differences of them at: http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/client.html

Import package:
```
import com.sproutigy.commons.jelasticsearch.client.*;
```

Use embedded engine:
```Java
ElasticsearchEmbedded es = new ElasticsearchEmbedded();
es.setPersistent(false); //keep documents in memory, do not store them
es.setHttpEnabled(true); //listen for HTTP REST requests (disabled by default)
```

or transport client:
```Java
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


More about Elasticsearch Java API:
http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/index.html


TODO
--------------
jElasticsearch will soon provide object-oriented JSON mappings builder.


Maven
--------------

Maven repository location: **http://maven.sproutigy.com/**
```XML
    <repositories>
        <repository>
            <id>Sproutigy</id>
            <name>Sproutigy Repository</name>
            <url>http://maven.sproutigy.com/</url>
        </repository>
    </repositories>
```

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
    <artifactId>jelasticsearch-connectors-transport</artifactId>
    <version>${elasticsearch.version}</version>
</dependency>
```

- ElasticsearchNode:
```XML
<dependency>
    <groupId>com.sproutigy.commons.jelasticsearch</groupId>
    <artifactId>jelasticsearch-connectors-node</artifactId>
    <version>${elasticsearch.version}</version>
</dependency>
```

- ElasticsearchDataNode:
```XML
<dependency>
    <groupId>com.sproutigy.commons.jelasticsearch</groupId>
    <artifactId>jelasticsearch-connectors-datanode</artifactId>
    <version>${elasticsearch.version}</version>
</dependency>
```

- ElasticsearchEmbedded:
```XML
<dependency>
    <groupId>com.sproutigy.commons.jelasticsearch</groupId>
    <artifactId>jelasticsearch-connectors-embedded</artifactId>
    <version>${elasticsearch.version}</version>
</dependency>
```
