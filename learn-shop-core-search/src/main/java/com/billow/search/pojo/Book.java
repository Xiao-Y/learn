package com.billow.search.pojo;

import com.billow.search.constant.AnalyzerConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Document(indexName = "book")
public class Book {
    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    private String name;

    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    private String title;

    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    private String desc;
}