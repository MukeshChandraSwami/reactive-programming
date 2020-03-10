package com.learn.reactive.entity;

import com.learn.reactive.constants.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Data
@Document(collection = "notification")
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEO {
    private String id = UUID.randomUUID().toString() + (new Date().getTime() * (long)Math.random());
    @CreatedDate
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date createdAt;
    @LastModifiedDate
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    private String title;
    private String description;
    private NotificationType notificationType;
}
