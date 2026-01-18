package com.commerceteamproject.customer.dto;

import com.commerceteamproject.customer.entity.Customer;
import com.commerceteamproject.customer.entity.CustomerStatus;
import com.commerceteamproject.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

// 리뷰 테스트 용도의 가상 고객 계정 생성
@Component
@RequiredArgsConstructor
public class TestCustomerInitializer implements ApplicationRunner {

    private final CustomerRepository customerRepository;

    @Override
    public void run(ApplicationArguments args) {

        // 이미 고객이 있으면 생성 안 함
        if (customerRepository.count() > 0) {
            return;
        }

        Customer testCustomer = new Customer(
                "테스트 고객",
                "customer@test.com",
                "010-0000-0000",
                CustomerStatus.ACTIVE
        );

        customerRepository.save(testCustomer);
    }
}