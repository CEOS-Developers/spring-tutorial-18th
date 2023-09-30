package com.ceos18.springboot.database;

import com.ceos18.springboot.database.base.BaseTimeEntity;
import com.ceos18.springboot.database.enums.ClothesSize;
import com.ceos18.springboot.database.enums.TradingCode;
import com.ceos18.springboot.database.enums.StatusCode;
import com.ceos18.springboot.database.enums.TradingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "PROD_LIST")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 테스트만 하고 빌더패턴 다시 구현할게요 ㅜㅜ
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROD_NO")
    @NotNull
    private Long productNo;

    @ManyToOne
    @JoinColumn(name = "USR_NO")
    private User userNo;

    @Column(name = "PROD_TIT")
    @NotNull
    private String title;

    @Column(name = "PROD_KEYW", length = 10)
    @NotNull
    @Size(max = 10)
    private String keyword;

    @Column(name = "PROD_CONT", columnDefinition = "TEXT")
    @NotNull
    private String content;

    // Cell, Share
    @Enumerated(EnumType.STRING)
    @Column(name = "TRD_CD", columnDefinition = "CHAR(5)")
    @Size(max = 5)
    @NotNull
    private TradingCode tradingCode;

    // 거래 방식 밑에 체크 했는지
    @Enumerated(EnumType.STRING)
    @Column(name = "TRD_OPT_CD", columnDefinition = "CHAR(1)")
    @Size(max = 1)
    @NotNull
    private StatusCode tradingOptionCode;

    @Column(name = "PROD_PRC", length = 10)
    @Size(max = 10)
    @NotNull
    private int price;

    @Column(name = "PROD_BRN", length = 20)
    @Size(max = 20)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROD_SIZ", columnDefinition = "CHAR(4)")
    @Size(max = 4)
    private ClothesSize size;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRD_STAT", columnDefinition = "CHAR(11)")
    @ColumnDefault("'PROCEEDING'")
    @NotNull
    @Size(max = 11)
    private TradingStatus tradingStatus;
}
