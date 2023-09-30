package com.ceos18.springboot.domain;

import com.ceos18.springboot.domain.base.BaseTimeEntity;
import com.ceos18.springboot.domain.enums.ReviewStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "REVW_LIST")
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVW_NO")
    private Long reviewNo;

    @ManyToOne
    @JoinColumn(name = "USR_NO")
    @NotNull
    private User userNo;

    @ManyToOne
    @JoinColumn(name = "PROD_NO")
    @NotNull
    private Product productNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "REVW_STAT", columnDefinition = "CHAR(5)")
    @NotNull
    private ReviewStatus reviewStatus;

    @Column(name = "REVW_LINE", length = 30)
    @NotNull
    @Size(max = 30)
    private String oneLineReview;

    @Column(name = "REVW_CONT", columnDefinition = "TEXT")
    private String content;

    @Column(name = "REVW_IMG_URL", length = 200)
    @Size(max = 200)
    private String reviewImageUrl;
}
