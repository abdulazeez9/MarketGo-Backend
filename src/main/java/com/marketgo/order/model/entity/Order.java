package  com.marketgo.order.model.entity;

import com.marketgo.common.entity.BaseEntity;
import com.marketgo.profile.buyer.model.entity.BuyerProfile;
import com.marketgo.profile.runner.model.entity.RunnerProfile;
import com.marketgo.user.model.entity.Address;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private BuyerProfile buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "runner_id", nullable = true)
    private RunnerProfile runner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.pending;

    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.unpaid;

    private String otpCode;
    private boolean otpVerified = false;

    public enum PaymentMethod { wallet, card, transfer, cash_on_delivery }
    public enum OrderStatus { pending, confirmed, runner_assigned, picked_up, delivered, completed, cancelled }
    public enum PaymentStatus { unpaid, paid, pending_cod, refunded }
}