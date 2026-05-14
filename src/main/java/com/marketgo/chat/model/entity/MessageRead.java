package com.marketgo.chat.model.entity;


import com.marketgo.common.entity.BaseEntity;
import com.marketgo.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "message_reads",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"message_id", "user_id"})
        })
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MessageRead extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime readAt;
}