package com.commerceteamproject.order.service;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.entity.Admin;
import com.commerceteamproject.admin.repository.AdminRepository;
import com.commerceteamproject.customer.entity.Customer;
import com.commerceteamproject.customer.exception.CustomerNotFoundException;
import com.commerceteamproject.customer.repository.CustomerRepository;
import com.commerceteamproject.order.dto.CreateOrderRequest;
import com.commerceteamproject.order.dto.CreateOrderResponse;
import com.commerceteamproject.order.entity.Order;
import com.commerceteamproject.order.repository.OrderRepository;
import com.commerceteamproject.product.entity.Product;
import com.commerceteamproject.product.entity.ProductStatus;
import com.commerceteamproject.product.exception.ProductNotFoundException;
import com.commerceteamproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    // CS 주문 생성
    @Transactional
    public CreateOrderResponse save(SessionAdmin sessionAdmin, CreateOrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
                () -> new CustomerNotFoundException("존재하지 않는 고객입니다.")
        );
        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("존재하지 않는 상품입니다.")
        );
        Admin admin = adminRepository.findById(sessionAdmin.getId()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 관리자입니다.")
        );
        if (product.getStatus().equals(ProductStatus.SOLD_OUT)) {
            throw new IllegalStateException("해당 상품은 품절되었습니다.");
        } else if (product.getStatus().equals(ProductStatus.DISCONTINUED)) {
            throw new IllegalStateException("해당 상품은 단종되었습니다.");
        } else if (product.getStock() < request.getQuantity()) {
            throw new IllegalStateException("남은 재고보다 주문 수량이 많습니다.");
        }
        Order order = new Order(
                customer,
                product,
                request.getQuantity(),
                product.getPrice() * request.getQuantity(),
                admin
        );
        Order savedOrder = orderRepository.save(order);
        return new CreateOrderResponse(
                savedOrder.getId(),
                savedOrder.getOrderNumber(),
                savedOrder.getCustomer().getId(),
                savedOrder.getCustomer().getName(),
                savedOrder.getProduct().getId(),
                savedOrder.getProduct().getName(),
                savedOrder.getQuantity(),
                savedOrder.getAmount(),
                savedOrder.getOrderStatus(),
                savedOrder.getCreatedAt(),
                savedOrder.getAdmin().getId(),
                savedOrder.getAdmin().getName()
        );
    }
}
