package com.ceos18.springboot.post.entity;

import com.ceos18.springboot.user.entity.User;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlistId")
    private int wishlistId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "postId", referencedColumnName = "postId", nullable = false)
    private Post post;

    @Column(name = "created", nullable = false)
    private Timestamp created;

    @Column(name = "updated", nullable = false)
    private Timestamp updated;

    @Column(name = "status", nullable = false, length = 10)
    private String status;

}
