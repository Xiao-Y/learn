## 查询语句拼接实战termQuery ,matchQuery, boolQuery, rangeQuery, wildcardQuery

1，启动es的服务。在进行索引查询之前，首先建立索引库， 并向索引库中添加测试的索引信息。
执行以下的命令后:
(1)创建了INDEX = store,TYPE = books的索引库 
(2) 向索引库中添加了id=1，以及id=2 的两条索引记录。

```bash
curl -XPUT 'http://192.168.25.101:9200/store/books/1' -d '{
 "title": "Elasticsearch: The Definitive Guide",
 "name" : {
 	"first" : "Zachary",
 	"last" : "Tong"
 },
 "publish_date":"2015-02-06",
 "price":"49.99"
 }'
```

　　

```bash
#在添加一个书的信息
 curl -XPUT 'http://192.168.25.101:9200/store/books/2' -d '{
 "title": "Elasticsearch Blueprints",
 "name" : {
 	"first" : "Vineeth",
 	"last" : "Mohan"
 },
 "publish_date":"2015-06-06",
 "price":"35.99"
 }'
```

(1) 索引添加完成后，可以通过curl命令查询，上述索引是否添加成功。

```bash
 #在linux中通过curl的方式查询
 curl -XGET 'http://192.168.25.101:9200/store/books/1'
```



2，构造termQuery对象进行字符的精确匹配查询

(1) 下面是用es的termQuery对象构造查询语句，精确查询price = “35.99”的书籍信息。
以下es查询语句相当于 sql语句：
select * from books where price = 35.99。
下面的代码中首先是获取transportClient对象，之后构造searchRequestBuilder的对象， 
然后通过searchRequestBuilder对象发送http请求进行查询。
下面代码运行后打印出来的searchRequesrBuilder 的内容如下：

```bash
@Test
public void termsearch() {
    TransportClient client = null;
    try {
        client = ESConnectFactory.getTransportClient("192.168.25.101");
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch("store")
            .setTypes("books")
            .setQuery(QueryBuilders.termQuery("price", "35.99"));
        System.out.println(searchRequestBuilder);
        SearchHit[] hits = searchRequestBuilder.get().getHits().getHits();
        for(SearchHit hit : hits){
            System.out.println(hit.getSourceAsString());
        }
    }catch (Exception e){
        e.printStackTrace();
    }
}
#代码运行后，控制台打印出来的searchRequestBuilder对象
{
  "query" : {
    "term" : {
      "price" : "35.99"
    }
  }
}
```

(2) 其实借助上面的searchRequestBuilder对象，我们还可以用curl命令进行精确匹配查询。两者的查询结果是一致的。如下：

```bash
curl -XGET 'http://192.168.25.101:9200/store/books/_search' -d '{
"query" : {
     "term" : {
     "price" : "35.99"
     }
}
}'
```

　

3，通过termsQuery实现指定多个值进行精确匹配查询

下面的代码与上述termQuery代码很相似，只有构造query对象时有不同的地方。
我这边这展示不同地方:
QueryBuilder.termsQuery()方法有好几个重载的方法，第二参数可以传入一个可变参数类型， 也可以传入一个集合类型。
以下的es语句相当于sql 语句：
select * from book where price in in ("35.99","49.99")

```bash
  SearchRequestBuilder searchRequestBuilder = client.prepareSearch("store")
                    .setTypes("books")
                    .setQuery(QueryBuilders.termsQuery("price", "35.99","49.99")) //传入可变参数类型
 List<String> price = new ArrayList<>();
 price.add("35.99");
 price.add("49.99");
 SearchResponse response = client.prepareSearch("store")
                    .setTypes("books")
                    .setQuery(QueryBuilders.termsQuery("price", price))       // 传入一个集合类型
                    .get();
```

(1) 其中searchRequestBuider打印出来如下：

```bash
{
  "query" : {
    "terms" : {
      "price" : [ "35.99", "49.99" ]
    }
  }
}
```

(2)与java代码等同的curl命令如下：

```bash
curl -XGET 'http://172.16.0.14:9200/store/books/_search' -d '{
"query" : {
     "terms" : {
     	"price" : ["35.99", "49.99"]
     }
}
}'
```



4, boolQuery查询

先构造boolQuery的对象，然后在boolQuery对象里面添加逻辑判断条件。boolquery嵌套的条件有以下类型:

(1) must: 条件必须满足，相当于 and

(2) should: 条件可以满足也可以不满足，相当于 or

(3) must_not: 条件不需要满足，相当于 not。

以下的es语句相当于sql语句: SELECT * FROM books WHERE (price = 35.99 OR price = 49.99) AND (publish_date != "2016-02-06")

```bash
  SearchRequestBuilder searchRequestBuilder = client.prepareSearch("store")
                    .setTypes("books")
                    .setQuery(QueryBuilders.boolQuery()
                            .should(QueryBuilders.termQuery("price", "35.99"))
                            .should(QueryBuilders.termQuery("price", "49.99"))
                            .mustNot(QueryBuilders.termQuery("publish_date", "2016-02-06")));// Query
```

(1)searchRequestBuilder对象打印出来如下：

```bash
{
  "query" : {
    "bool" : {
      "must_not" : {
        "term" : {
          "publish_date" : "2016-02-06"
        }
      },
      "should" : [ {
        "term" : {
          "price" : "35.99"
        }
      }, {
        "term" : {
          "price" : "49.99"
        }
      } ]
    }
  }
}
```

　

5，嵌套查询

(1)这次先确定sql语句的写法，然后根据将确定的sql语句改写成es的语法形式。sql语句如下: SELECT * FROM books WHERE price = "35.99" OR ( publish_date = "2016-02-06" AND price =
"49.99" ) ，先把上述的sql语句拆成两部分 (1)price = "35.99"

(2) publish_date = "2016-02-06" AND price = "49.99" ，这两部分中间是用OR连接，拆分后各自用should连接。然后再将 AND 关键字两边拆开，各自用must连接。如下:

```bash
   SearchRequestBuilder searchRequestBuilder = client.prepareSearch("store")
                    .setTypes("books")
                    .setQuery(QueryBuilders.boolQuery()
                            .should(QueryBuilders.termQuery("price", "35.99"))
                            .should(QueryBuilders.boolQuery()
                                     .must(QueryBuilders.termQuery("price","49.99"))
                                     .must(QueryBuilders.termQuery("publish_date","2016-02-06"))));
```

(2)searchRequestBuilder 打印结果如下：

```bash
{
  "query" : {
    "bool" : {
      "should" : [ {
        "term" : {
          "price" : "35.99"
        }
      }, {
        "bool" : {
          "must" : [ {
            "term" : {
              "price" : "49.99"
            }
          }, {
            "term" : {
              "publish_date" : "2016-02-06"
            }
          } ]
        }
      } ]
    }
  }
}
```

　

6，rangeQuery范围的过滤

在构造rangeQuery对象时，相应搜索的field不能是String类型的，需要的field是数值类型。所以我需要在原来的类型中新增一个int类型的field: pageSize， 新增一个索引文档id = 3：

```bash
curl -XPUT 'http://192.168.25.101:9200/store/books/3' -d '{
 "title": "The third book",
 "name" : {
     "first" : "Zachary",
     "last" : "Tong"
 },
 "publish_date":"2018-02-06",
 "price":"49.99",
 "pageSize":200
 }'
```

(1) rangeQuery("pageSize").from(100).to(300)， 过滤pageSize在 [100, 300]范围内的数据，两边都是闭区间。

```bash
  SearchRequestBuilder searchRequestBuilder = client.prepareSearch("store")
                    .setTypes("books")
                    .setQuery(QueryBuilders.matchAllQuery())
                    .setPostFilter(QueryBuilders.rangeQuery("pageSize").from(100).to(300));
```

(2)rangeQuery("pageSize").gt(100).lt(300) , （great than 100, less than 300） 过滤pageSize在(100, 300)范围内的数据，两边都是开区间。

rangeQuery("pageSize").gte(100).lte(300) (great than or equal 100, less than or equals 300) 过滤pageSize在 [100, 300]范围内的数据，两边都是闭区间。

```bash
 SearchRequestBuilder searchRequestBuilder = client.prepareSearch("store")
                    .setTypes("books")
                    .setQuery(QueryBuilders.matchAllQuery())
                    .setPostFilter(QueryBuilders.rangeQuery("pageSize").gt(100).lt(300));
```

(3) searchQueryBuilder的结构如下：

```bash
{
  "query" : {
    "match_all" : { }
  },
  "post_filter" : {
    "range" : {
      "pageSize" : {
        "from" : 100,
        "to" : 200,
        "include_lower" : true,
        "include_upper" : true
      }
    }
  }
}
```



7，模糊查询匹配

(1) es也提供了sql中类似于like语句的模糊匹配查询，通过wildcardQuery的语句来实现。

Implements the wildcard search query. Supported wildcards are <tt>*</tt>, which matches any character sequence (including the empty one), and <tt>?</tt>,which matches any single character. Note this query can be slow, as it needs to iterate over many terms. In order to prevent extremely slow WildcardQueries,a Wildcard term should not start with one of the wildcards <tt>*</tt> or <tt>?</tt>.

模糊匹配查询有两种匹配符，分别是" * " 以及 " ? "， 用" * "来匹配任何字符，包括空字符串。用" ? "来匹配任意的单个字符。当文档对象很多时，它需要遍历查询，用模糊搜索的查询速度会 很慢 。最好不要用* 或者 ？ 当做查询的开头字母，这种情况下速度会更加慢。

下面是搜索price是3开头的文档：(注意全文检索的字段，不能用模糊搜索去匹配，测试时全文搜索字段用模糊查询查询不到结果)

```bash
 SearchRequestBuilder searchRequestBuilder = client.prepareSearch("store")
                    .setTypes("books")
                    .setQuery(QueryBuilders.wildcardQuery("price", "3*"));// Query
```

searchRequestBuilder 具体结构如下:

```bash
{
  "query" : {
    "wildcard" : {
      "price" : "3*"
    }
  }
}
```

　　

8，matchQuery用于文本类型字段的搜索

matchQuery会将搜索条件按照标准分词器的规则分词，分完词之后分别搜索匹配项。(注意: 测试中全文检索字段如果用termQuery 或者 wildcardQuery 将不能查询成功。因为全文索引字段建立索引时已经被分词工具分成单个单词了)

(1)下面的代码搜索时，先将"The third"关键字进行分词，分成"The" 和"third" 然后分别和文档中title分词后的结果进行匹配。最终结果会匹配到 title= " Elasticsearch: The Definitive Guide" 以及 title ="The third book"的这两条记录。

```bash
  SearchRequestBuilder searchRequestBuilder = client.prepareSearch("store")
                    .setTypes("books")
                    .setQuery(QueryBuilders.matchQuery("title", "The third"));// Query
```

searchRequestBuilder 对象的具体结构如下:

```bash
{
  "query" : {
    "match" : {
      "title" : {
        "query" : "The thrid",
        "type" : "boolean"
      }
    }
  }
}
```

　　