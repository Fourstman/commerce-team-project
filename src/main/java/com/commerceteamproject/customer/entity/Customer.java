package com.commerceteamproject.customer.entity;

import com.commerceteamproject.common.BaseEntity;
import com.commerceteamproject.customer.dto.UpdateCustomerInformationRequest;
import com.commerceteamproject.customer.dto.UpdateCustomerStateRequest;
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
    private CustomerState state;

    public Customer(String name, String email, String phone, CustomerState state) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.state = state;
    }

    public void updateInformation(@Valid UpdateCustomerInformationRequest request) {
        this.name = request.getName();
        this.email = request.getEmail();
        this.phone = request.getPhone();
    }

    public void updateState(@Valid UpdateCustomerStateRequest request) {
        this.state = request.getState();
    }
}
