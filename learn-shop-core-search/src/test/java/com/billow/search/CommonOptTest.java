package com.billow.search;

import com.billow.search.common.cons.AnalyzerConstant;
import com.billow.search.pojo.po.Book;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;

/**
 * @author liuyongtao
 * @since 2020-12-25 10:50
 */
@Slf4j
public class CommonOptTest extends CoreAppTests {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void test() {
        log.info("----->>>> test");
    }

    @Test
    public void createIndex() throws Exception {
        String indexName = "userinfo";
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("properties")
                .startObject()
                .field("name").startObject().field("index", "true").field("type", "keyword").endObject()
                .field("age").startObject().field("index", "true").field("type", "integer").endObject()
                .field("money").startObject().field("index", "true").field("type", "double").endObject()
                .field("address").startObject().field("index", "true").field("type", "text").field("analyzer", AnalyzerConstant.ANALYZER).endObject()
                .field("birthday").startObject().field("index", "true").field("type", "date").field("format", "strict_date_optional_time||epoch_millis").endObject()
                .endObject()
                .endObject();

        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.mapping(builder);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
        System.out.println("是否创建成功：" + acknowledged);
    }

    @Test
    public void createIndex2() throws Exception {
        IndexOperations indexOps = elasticsearchRestTemplate.indexOps(Book.class);
        boolean b = indexOps.create();
        System.out.println("是否创建成功：" + b);
    }
}
