package com.ceos18.springboot.post.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum PostStatus {
    IN_PROGRESS("판매중"),
    RESERVED("예약중"),
    DONE("거래완료");

    private final String statusName;

}