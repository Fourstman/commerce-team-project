package com.commerceteamproject.customer.entity;

import com.commerceteamproject.common.entity.BaseEntity;
import com.commerceteamproject.customer.dto.UpdateCustomerInformationRequest;
import com.commerceteamproject.customer.dto.UpdateCustomerStatusRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "customers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    private int totalOrderAmount = 0;
    private int totalOrderCount = 0;

    public void updateInformation(UpdateCustomerInformationRequest request) {
        if (request.getName() != null && !request.getName().isBlank()) {
            this.name = request.getName();
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            this.email = request.getEmail();
        }
        if (request.getPhone() != null) {
            this.phone = request.getPhone();
        }
    }

    public void updateStatus(UpdateCustomerStatusRequest request) {
        this.status = request.getStatus();
    }

    public void addOrder(int amount) {
        this.totalOrderAmount += amount;
        this.totalOrderCount++;
    }

    public void cancelOrder(int amount) {
        this.totalOrderAmount -= amount;
        this.totalOrderCount--;
    }

    // 가상 테스트용 생성
    public Customer(String name, String email, String phone, CustomerStatus status) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }
}
