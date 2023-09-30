package com.ceos18.springboot.database;

import com.ceos18.springboot.database.base.BaseTimeEntity;
import com.ceos18.springboot.database.enums.StatusCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "USR_LIST")
public class User extends BaseTimeEntity {
    @Id
    @Column(name = "USR_NO", length = 12)
    @NotNull
    @Size(max = 12)
    private String userNo;

    @Column(name = "NIC_NM", length = 12, unique = true)
    @Size(max = 12, message = "닉네임은 12자 이하로 입력해주세요.")
    @NotNull
    private String nickName;

    @Column(name = "PH_NM", length = 12, unique = true)
    @NotNull
    @Size(max = 12)
    private String phoneNumber;

    @Column(name = "IMG_URL", length = 200)
    @Size(max = 200)
    private String userImageUrl;

    @Column(name = "ADR", length = 50)
    @NotNull
    @Size(max = 50)
    private String address;

    @Column(name = "MNN_TEMP", columnDefinition = "CHAR(4)")
    @NotNull
    @ColumnDefault("'36.5'")
    @Size(max = 4)
    private String mannerTemperature;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADR_CERT_YN", columnDefinition = "CHAR(1)")
    @NotNull
    @ColumnDefault("'N'")
    @Size(max = 1)
    private StatusCode addressCertificationYN;

    // 등록한 사용자 소프트웨어 식별 정보
    @Column(name = "UA", length = 50)
    @NotNull
    @Size(max = 50)
    private String userAgent;

    // 개인정보 동의
    @Enumerated(EnumType.STRING)
    @Column(name = "PRI_YN", columnDefinition = "CHAR(1)")
    @NotNull
    @ColumnDefault("'Y'")
    @Size(max = 1)
    private StatusCode privacyYN;

    // Y: 마케팅 수신 동의, N: 마케팅 수신 미동의
    @Enumerated(EnumType.STRING)
    @Column(name = "MKTG_YN", columnDefinition = "CHAR(1)")
    @NotNull
    @Size(max = 1)
    private StatusCode marketingYN;
}
