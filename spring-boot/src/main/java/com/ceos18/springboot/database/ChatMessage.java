package com.ceos18.springboot.database;

import com.ceos18.springboot.database.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity(name = "CHAT_MSG_LIST")
public class ChatMessage extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MSG_NO")
    private Long messageNo;

    @ManyToOne
    @JoinColumn(name = "SEND_NO")
    @NotNull
    private User sender;

    @ManyToOne
    @JoinColumn(name = "CHAT_NO")
    @NotNull
    private Chat chat;

    @Column(name = "MSG_CONT", columnDefinition = "TEXT")
    @NotNull
    private String content;
}
