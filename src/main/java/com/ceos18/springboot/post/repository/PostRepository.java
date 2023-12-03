package com.ceos18.springboot.post.repository;

import com.ceos18.springboot.post.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // keyword를 포함하는 제목 또는 내용을 갖고 있는 게시글 찾기
    // ContainingIgnoreCase로 대소문자 미구분
    List<Post> findByPostTitleContainingIgnoreCase(String keyword);
    List<Post> findByPostContentContainingIgnoreCase(String keyword);

}
