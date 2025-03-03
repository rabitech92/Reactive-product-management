package com.supershop.managemen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String id;
    private String customerId;
    private String productId;
    private int quantity;
    private double totalPrice;
    private String status;
    private String deliveryAddress;
    private String orderDate;
    private String deliveryDate;
    private String paymentMethod;
    private String shippingStatus;
    private String trackingNumber;
    private String comments;
    private String paymentStatus;
    private String deliveryStatus;
    private String refundStatus;
}
