package com.ceos18.springboot.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NormalResponseDto {
    private String status;
    private String message;

    protected NormalResponseDto(String status) {
        this.status = status;
    }

    public static NormalResponseDto success() {
        return new NormalResponseDto("SUCCESS");
    }

    public static NormalResponseDto fail() {
        return new NormalResponseDto("FAIL");
    }

    // TODO: 로그인 구현 후 메서드 사용 예정
    public void setMessage(String message) {
        this.message = message;
    }
}
