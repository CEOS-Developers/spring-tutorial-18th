package com.ceos18.springboot.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum PostStatus {
    IN_PROGRESS("판매중"),
    RESERVED("예약중"),
    DONE("거래완료");

    private final String statusName;

}