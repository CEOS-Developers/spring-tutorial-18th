package com.ceos18.springboot.user.entity;

import com.ceos18.springboot.common.entity.BaseTimeEntity;

import lombok.*;

import java.math.BigDecimal;
import jakarta.persistence.*;


@Entity
@Getter
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profileimg")
    private String imgUrl;

    @Column(name = "temperature", nullable = false, columnDefinition = "DECIMAL(4, 2) DEFAULT 36.5")
    private BigDecimal temperature;


}
