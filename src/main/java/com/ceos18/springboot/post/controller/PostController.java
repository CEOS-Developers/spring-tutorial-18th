package com.ceos18.springboot.post.controller;

import com.ceos18.springboot.post.entity.Post;
import com.ceos18.springboot.post.repository.PostRepository;
import com.ceos18.springboot.post.response.PostResponseDto;
import com.ceos18.springboot.post.service.PostService;
import com.ceos18.springboot.common.dto.NormalResponseDto;
import com.ceos18.springboot.post.request.PostRequestDto;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;


@Controller
@AllArgsConstructor
@RequestMapping("/api/board/posts")
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    // 상품 게시판

    // 상품 게시글 작성
    @PostMapping("")
    public ResponseEntity<NormalResponseDto> createPost(@RequestBody @Valid PostRequestDto requestDto) {

        postService.createPost(requestDto);
        return ResponseEntity.ok(NormalResponseDto.success());
    }

    // 상품 게시글 전체 조회
    @GetMapping("")
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> responseDtos = postService.getAllPosts();
        return ResponseEntity.ok(responseDtos);
    }

    // 상품 게시글 조건별 조회
    @GetMapping("/{postId}")
    public ResponseEntity<List<PostResponseDto>> searchPosts(@RequestParam(value = "keyword",
                                                                            required = false) String keyword) {
        List<PostResponseDto> searchResults = postService.searchPosts(keyword);
        return ResponseEntity.ok(searchResults);
    }

    // 상품 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<NormalResponseDto> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok(NormalResponseDto.success());
    }





}
