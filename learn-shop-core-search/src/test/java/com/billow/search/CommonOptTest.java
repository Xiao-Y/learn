package com.billow.search;

import com.billow.search.pojo.Book;
import lombok.extern.slf4j.Slf4j;
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

    @Test
    public void test() {
        log.info("----->>>> test");
    }

    @Test
    public void createIndex() {
        IndexOperations indexOps = elasticsearchRestTemplate.indexOps(Book.class);
        boolean b = indexOps.create();
        log.info("create index book:{}", b);
    }
}
