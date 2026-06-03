package com.marketgo.chat.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.order.model.entity.Order;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversations", indexes = {
        @Index(name = "idx_conv_order", columnList = "order_id"),
        @Index(name = "idx_conv_last_message", columnList = "last_message_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Conversation extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ConversationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String title;

    private LocalDateTime lastMessageAt;

    @OneToMany(mappedBy = "conversation",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<ConversationParticipant> participants = new ArrayList<>();

    @OneToMany(mappedBy = "conversation",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<Message> messages = new ArrayList<>();

    public enum ConversationType {
        direct, group, order_support
    }
}