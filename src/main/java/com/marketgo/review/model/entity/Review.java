package com.marketgo.review.model.entity;


import com.marketgo.common.entity.BaseEntity;
import com.marketgo.order.model.entity.Order;
import com.marketgo.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "reviews",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"order_id", "reviewer_id", "target_type"}
                )
        },
        indexes = {
                @Index(name = "idx_review_target", columnList = "target_id"),
                @Index(name = "idx_review_order", columnList = "order_id")
        })
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id", nullable = false)
    private User reviewer;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    private UUID targetId;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "text")
    private String comment;

    public enum TargetType {
        seller, runner, product
    }
}