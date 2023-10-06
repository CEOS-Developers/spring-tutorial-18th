package com.ceos18.springboot.post.request;

import com.ceos18.springboot.user.entity.User;
import com.ceos18.springboot.post.entity.Post;
import com.ceos18.springboot.post.entity.DealMethod;
import com.ceos18.springboot.post.entity.PostStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestDto {
    // private User user;

    private int townId;

    private int categoryId;

    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String postTitle;

    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String postContent;

    private int cost;

    private String productImage;

    private DealMethod dealMethod;

    private PostStatus postStatus;

    public Post toPost() {
        return Post.builder()
                // .user(user)
                .townId(townId)
                .categoryId(categoryId)
                .postTitle(postTitle)
                .postContent(postContent)
                .cost(cost)
                .productImage(productImage)
                .dealMethod(dealMethod)
                .postStatus(postStatus)
                .build();
    }
}