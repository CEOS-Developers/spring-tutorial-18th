package com.ceos18.springboot.domain;

import com.ceos18.springboot.domain.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity(name = "CHAT_MSG_LIST")
public class ChatMessage extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MSG_NO")
    private Long messageNo;

    @Column(name = "MSG_CONT", columnDefinition = "TEXT")
    @NotNull
    private String content;

    @ManyToOne
    @JoinColumn(name = "SEND_NO")
    @NotNull
    private User sender;

    @ManyToOne
    @JoinColumn(name = "CHAT_NO")
    @NotNull
    private Chat chat;
}
