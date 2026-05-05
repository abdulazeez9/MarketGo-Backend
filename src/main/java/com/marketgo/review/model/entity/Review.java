package com.marketgo.review.model.entity;


import com.marketgo.common.entity.BaseEntity;
import com.marketgo.order.model.entity.Order;
import com.marketgo.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id")
    private User reviewer;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    private UUID targetId; // polymorphic - points to seller, runner, or product

    @Column(columnDefinition = "tinyint")
    private Integer rating; // 1-5

    @Column(columnDefinition = "text")
    private String comment;

    public enum TargetType { seller, runner, product }
}