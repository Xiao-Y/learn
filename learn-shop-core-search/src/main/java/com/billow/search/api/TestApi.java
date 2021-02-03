package com.billow.search.api;

import com.billow.search.dao.BookDao;
import com.billow.search.pojo.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class TestApi {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private BookDao bookDao;


    @GetMapping("/test")
    public String test() {
        return "hello elasticsearch";
    }

    @GetMapping("/searchIndex")
    public boolean searchIndex() {
        IndexOperations indexOps = elasticsearchRestTemplate.indexOps(Book.class);
        boolean exists = indexOps.exists();
        return exists;
    }

    @DeleteMapping("/deleteIndex")
    public boolean deleteIndex() {
        IndexOperations indexOps = elasticsearchRestTemplate.indexOps(Book.class);
        boolean delete = indexOps.delete();
        return delete;
    }

    @PutMapping("/createIndex")
    public boolean createIndex() {
        IndexOperations indexOps = elasticsearchRestTemplate.indexOps(Book.class);
        boolean b = indexOps.create();
        log.info("=====>>>>> 是否创建成功：" + b);
        return b;
    }

    @PutMapping("/createMapping")
    public boolean createMapping() {
        IndexOperations indexOps = elasticsearchRestTemplate.indexOps(Book.class);
        Document mapping = indexOps.createMapping(Book.class);
        boolean b = indexOps.putMapping(mapping);
        return b;
    }

    @PutMapping("/putData")
    public Iterable<Book> putData() {
        List<Book> books = new ArrayList<>();

        Book book = new Book();
        book.setName("JAVA 编程思想");
        book.setTitle("JAVA");
        book.setDesc("全网第一畅销java书籍");
        books.add(book);

        Book book1 = new Book();
        book.setTitle("C++");
        book.setName("C++ 编程思想");
        book.setDesc("全网第一畅销C++书籍");
        books.add(book1);

        Iterable<Book> save = bookDao.saveAll(books);
        return save;
    }

    @GetMapping("/searchDataById/{id}")
    public Book searchDataById(@PathVariable("id") String id) {
        Optional<Book> byId = bookDao.findById(id);
        return byId.orElse(new Book());
    }

    @GetMapping("/searchData")
    public Iterable searchData() {
        Iterable<Book> all = bookDao.findAll();
        return all;
    }
}
