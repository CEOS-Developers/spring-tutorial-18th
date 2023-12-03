package com.ceos18.springboot.post.entity;

import com.ceos18.springboot.common.entity.BaseTimeEntity;
import com.ceos18.springboot.user.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import jakarta.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Post")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    TODO: 유저 로그인 구현 후 연동 예정
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
     */

    @Column(name = "town_id", nullable = false)
    private int townId;

    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @NotNull(message = "제목은 필수 입력값입니다.")
    @Column(name = "post_title", length = 200)
    private String postTitle;

    @NotNull(message = "내용은 필수 입력값입니다.")
    @Column(name = "post_content", length = 300)
    private String postContent;

    @NotNull(message = "가격은 필수 입력값입니다.")
    @Column(name = "product_cost")
    private int cost;

    @Column(name = "product_image")
    private String productImage;

    @Column(name = "product_deal-method", nullable = false)
    @Enumerated(EnumType.STRING)
    private DealMethod dealMethod;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    /*
    TODO: 가격 제안 로직 추가 여부 결정
    @Column(name = "hasPriceProposal")
    private Boolean hasPriceProposal;
     */

    @Builder
    public Post(Long id, int townId, int categoryId, String postTitle, String postContent, int cost, String productImage, DealMethod dealMethod, PostStatus postStatus) {
        this.id = id;
        // this.user = user;
        this.townId = townId;
        this.categoryId = categoryId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.cost = cost;
        this.productImage = productImage;
        this.dealMethod = dealMethod;
        this.postStatus = postStatus;
    }


}
