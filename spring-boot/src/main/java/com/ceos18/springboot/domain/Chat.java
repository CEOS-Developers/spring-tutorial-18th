package com.ceos18.springboot.domain;

import com.ceos18.springboot.domain.base.BaseTimeEntity;
import com.ceos18.springboot.domain.enums.StatusCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "CHAT_LIST")
public class Chat extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHAT_NO")
    private Long chatNo;

    @ManyToOne
    @JoinColumn(name = "SEND_NO")
    @NotNull
    private User sender;

    @ManyToOne
    @JoinColumn(name = "RECV_NO")
    @NotNull
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "CHAT_PIN_YN", columnDefinition = "CHAR(1)")
    @ColumnDefault("'N'")
    @NotNull
    @Size(max = 1)
    private StatusCode pinYN;

    @Enumerated(EnumType.STRING)
    @Column(name= "CHAT_ALM_YN", columnDefinition = "CHAR(1)")
    @ColumnDefault("'Y'")
    @NotNull
    @Size(max = 1)
    private StatusCode alarmYN;

    @Enumerated(EnumType.STRING)
    @Column(name = "CHAT_BLK", columnDefinition = "CHAR(1)")
    @ColumnDefault("'N'")
    @NotNull
    @Size(max = 1)
    private StatusCode blockYN;
}
