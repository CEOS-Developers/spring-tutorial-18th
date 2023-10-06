package com.ceos18.springboot.post.response;

import com.ceos18.springboot.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {
    private Long id;
    private int townId;
    private int categoryId;
    private String postTitle;
    private String postContent;
    private int cost;
    private String productImage;
    private String dealMethod;
    private String postStatus;
    private String createdAt;

    public static PostResponseDto of(Post post) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String createdAtString = post.getCreatedAt().format(formatter);

        return PostResponseDto.builder()
                .id(post.getId())
                .townId(post.getTownId())
                .categoryId(post.getCategoryId())
                .postTitle(post.getPostTitle())
                .postContent(post.getPostContent())
                .cost(post.getCost())
                .productImage(post.getProductImage())
                .dealMethod(post.getDealMethod().name())
                .postStatus(post.getPostStatus().name())
                .createdAt(createdAtString)
                .build();
    }

}
