package com.ceos18.springboot.post.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DealMethod {
    SELL("판매하기"),
    SHARE("나눔하기");

    private final String displayName;

}
