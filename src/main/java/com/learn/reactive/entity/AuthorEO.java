package com.learn.reactive.entity;

import com.learn.reactive.entity.commons.BaseEO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "author")
@AllArgsConstructor
@NoArgsConstructor
public class AuthorEO extends BaseEO {

    private String name;
    private String contactNum;
    private List<String> books;
}
