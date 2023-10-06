package com.ceos18.springboot.post.entity;

import com.ceos18.springboot.user.entity.User;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private int postId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "townId", nullable = false)
    private int townId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "categoryId", nullable = false)
    private int categoryId;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "dealMethod", nullable = false)
    @Enumerated(EnumType.STRING)
    private DealMethod dealMethod;

    @Column(name = "hasPriceProposal")
    private Boolean hasPriceProposal;

    @Column(name = "productImage")
    private String productImage;

    @Column(name = "created", nullable = false)
    private Timestamp created;

    @Column(name = "updated", nullable = false)
    private Timestamp updated;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

}
