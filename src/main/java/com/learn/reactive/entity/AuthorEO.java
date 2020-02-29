package com.learn.reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "author")
@AllArgsConstructor
@NoArgsConstructor
public class AuthorEO {

    @Id
    private String id;
    @Version
    private Long version;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    private String createdBy;
    private String updatedBy;

    private String name;
    private String contactNum;
    private List<String> books;
}
