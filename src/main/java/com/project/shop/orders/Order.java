package com.project.shop.orders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.shop.constant.OrderStatus;
import com.project.shop.entity.Member;

import jakarta.persistence.*;

import lombok.*;

@Getter @ToString
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate;    //주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;    //주문상태
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
}