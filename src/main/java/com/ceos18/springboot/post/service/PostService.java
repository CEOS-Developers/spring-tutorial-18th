package com.ceos18.springboot.post.service;

import com.ceos18.springboot.post.entity.Post;
import com.ceos18.springboot.post.request.PostRequestDto;
import com.ceos18.springboot.post.repository.PostRepository;
import com.ceos18.springboot.exception.KarrotException;
import com.ceos18.springboot.exception.ErrorCode;


import com.ceos18.springboot.post.response.PostResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 판매 게시글 작성
    public void createPost(PostRequestDto requestDto) {
        Post productPost = requestDto.toPost();
        postRepository.save(productPost);
    }

    // 상품 게시글 전체 조회
    public List<PostResponseDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();

        List<PostResponseDto> responseDtos = posts.stream()
                .map(PostResponseDto::of)
                .collect(Collectors.toList());

        return responseDtos;
    }

    // 상품 게시글 조건별 조회
    public List<PostResponseDto> searchPosts(String keyword) {
        // 키워들르 포함하고 있는 제목이나 내용으로 게시글 찾기
        // 대소문자 미구분
        List<Post> byTitle = postRepository.findByPostTitleContainingIgnoreCase(keyword);
        List<Post> byContent = postRepository.findByPostContentContainingIgnoreCase(keyword);

        List<PostResponseDto> responseDtos = new ArrayList<>();

        for (Post post : byTitle) {
            responseDtos.add(PostResponseDto.of(post));
        }

        for (Post post : byContent) {
            responseDtos.add(PostResponseDto.of(post));
        }

        // 중복 제거하기 위해 Set로 변환
        Set<PostResponseDto> filteredResponseDtos = new HashSet<>(responseDtos);

        // 중복 제거 후 다시 List로 변환
        List<PostResponseDto> filtereResponseDtoList = new ArrayList<>(filteredResponseDtos);

        return filtereResponseDtoList;

    }

    // 상품 게시글 삭제
    public void deletePost(Long postId) {
        // ID로 게시글 조히
        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            postRepository.delete(post);
        } else {
            // 게시글 존재하지 않을 시 에러 반환
            throw new KarrotException(ErrorCode.POST_NOT_FOUND);
        }
    }

}
