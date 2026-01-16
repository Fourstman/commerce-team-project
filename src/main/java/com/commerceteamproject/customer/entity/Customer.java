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
}
