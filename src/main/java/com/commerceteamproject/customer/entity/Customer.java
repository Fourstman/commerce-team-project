package com.commerceteamproject.customer.entity;

import com.commerceteamproject.common.entity.BaseEntity;
import com.commerceteamproject.customer.dto.UpdateCustomerInformationRequest;
import com.commerceteamproject.customer.dto.UpdateCustomerStatusRequest;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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

    public Customer(String name, String email, String phone, CustomerStatus status) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public void updateInformation(@Valid UpdateCustomerInformationRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.phone = request.getPhone();
    }

    public void updateStatus(@Valid UpdateCustomerStatusRequest request) {
        this.status = request.getStatus();
    }
}
