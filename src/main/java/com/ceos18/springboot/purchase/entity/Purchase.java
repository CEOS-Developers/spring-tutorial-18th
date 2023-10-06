package com.ceos18.springboot.purchase.entity;

import com.ceos18.springboot.post.entity.Post;
import com.ceos18.springboot.user.entity.User;
import com.ceos18.springboot.common.entity.BaseTimeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.persistence.*;

@Entity
@Getter
@Table(name = "Purchase")
@AllArgsConstructor
public class Purchase extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer_id")
    private User buyer;


}