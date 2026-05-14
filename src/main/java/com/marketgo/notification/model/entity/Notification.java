package com.marketgo.notification.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications", indexes = {
        @Index(name = "idx_notification_user", columnList = "user_id"),
        @Index(name = "idx_notification_read", columnList = "is_read")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String type;
    private String title;

    @Column(columnDefinition = "text")
    private String body;

    private boolean isRead = false;
}