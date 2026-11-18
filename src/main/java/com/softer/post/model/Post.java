package com.softer.post.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "post")
public class Post {

    @Id
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotEmpty
    @Column(nullable = false, length = 250)
    private String title;

    @NotEmpty
    @Column(nullable = false, columnDefinition = "text")
    private String body;

    @Version
    private Integer version;

    protected Post() {
    }

    public Post(Integer id, Integer userId, String title, String body, Integer version) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.version = version;
    }

    public Integer getId() { return id; }
    public Integer getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public Integer getVersion() { return version; }

    public void setId(Integer id) { this.id = id; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setTitle(String title) { this.title = title; }
    public void setBody(String body) { this.body = body; }

    public void updateContent(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
