package com.billow.search.dao;

import com.billow.search.pojo.po.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookDao extends ElasticsearchRepository<Book, String> {
}
