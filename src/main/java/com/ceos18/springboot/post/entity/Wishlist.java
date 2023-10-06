package com.ceos18.springboot.post.entity;

import com.ceos18.springboot.user.entity.User;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private Post post;

    // TODO: PostStauts로 변경
    @Column(name = "status", nullable = false, length = 10)
    private String status;

}
