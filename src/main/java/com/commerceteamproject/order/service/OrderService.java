package com.commerceteamproject.order.service;

import com.commerceteamproject.admin.dto.SessionAdmin;
import com.commerceteamproject.admin.entity.Admin;
import com.commerceteamproject.admin.exception.AdminNotFoundException;
import com.commerceteamproject.admin.repository.AdminRepository;
import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.common.exception.InvalidParameterException;
import com.commerceteamproject.customer.entity.Customer;
import com.commerceteamproject.customer.exception.CustomerNotFoundException;
import com.commerceteamproject.customer.repository.CustomerRepository;
import com.commerceteamproject.order.dto.*;
import com.commerceteamproject.order.entity.Order;
import com.commerceteamproject.order.entity.OrderStatus;
import com.commerceteamproject.order.exception.InsufficientStockException;
import com.commerceteamproject.order.exception.OrderNotFoundException;
import com.commerceteamproject.order.exception.OrderStatusNotAllowedException;
import com.commerceteamproject.order.repository.OrderRepository;
import com.commerceteamproject.product.entity.Product;
import com.commerceteamproject.product.entity.ProductStatus;
import com.commerceteamproject.product.exception.ProductNotFoundException;
import com.commerceteamproject.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                () -> new AdminNotFoundException("존재하지 않는 관리자입니다.")
        );
        if (product.getStatus().equals(ProductStatus.SOLD_OUT)) {
            throw new OrderStatusNotAllowedException("해당 상품은 품절되었습니다.");
        } else if (product.getStatus().equals(ProductStatus.DISCONTINUED)) {
            throw new OrderStatusNotAllowedException("해당 상품은 단종되었습니다.");
        } else if (product.getStock() < request.getQuantity()) {
            throw new InsufficientStockException("남은 재고보다 주문 수량이 많습니다.");
        }
        product.updateStock(product.getStock() - request.getQuantity());
        if (product.getStock() == 0) {
            product.changeStatus(ProductStatus.SOLD_OUT);
        }
        Order order = new Order(
                customer,
                product,
                request.getQuantity(),
                product.getPrice() * request.getQuantity(),
                admin
        );
        Order savedOrder = orderRepository.save(order);
        customer.addOrder(savedOrder.getAmount());
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

    // 주문 리스트 조회
    @Transactional(readOnly = true)
    public PageResponse<GetOrderListResponse> findOrders(
            String keyword, OrderStatus status, Pageable pageable) {
        List<String> allowedProperties = List.of("quantity", "amount", "createdAt");
        pageable.getSort().forEach(order -> {
            if (!allowedProperties.contains(order.getProperty())) {
                throw new InvalidParameterException("잘못된 정렬 기준입니다.");
            }
        });
        Pageable basePageable = PageRequest.of(
                pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
        Page<Order> orders = orderRepository.findByKeywordAndStatus(keyword, status, basePageable);
        Page<GetOrderListResponse> page = orders.map(o -> new GetOrderListResponse(
                o.getId(),
                o.getOrderNumber(),
                o.getCustomer().getName(),
                o.getProduct().getName(),
                o.getQuantity(),
                o.getAmount(),
                o.getCreatedAt(),
                o.getOrderStatus(),
                o.getAdmin().getName()
        ));
        return new PageResponse<>(page);
    }

    // 주문 상세 조회
    @Transactional(readOnly = true)
    public GetOneOrderResponse findOne(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("존재하지 않는 주문입니다.")
        );
        return new GetOneOrderResponse(
                order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getCustomer().getEmail(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getAmount(),
                order.getCreatedAt(),
                order.getOrderStatus(),
                order.getAdmin().getName(),
                order.getAdmin().getEmail(),
                order.getAdmin().getAdminRole()
        );
    }

    // 주문 상태 변경 / 주문 상태가 취소일 경우 상태 변경 불가
    @Transactional
    public void updateStatus(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("존재하지 않는 주문입니다.")
        );
        switch (order.getOrderStatus()) {
            case CANCEL -> throw new OrderStatusNotAllowedException("취소된 상품은 상태를 변경할 수 없습니다.");
            case PREPARE -> order.updateOrderStatus(OrderStatus.IN_DELIVERY);
            case IN_DELIVERY -> order.updateOrderStatus(OrderStatus.COMPLETE);
            case COMPLETE -> throw new OrderStatusNotAllowedException("배송 완료된 상품은 상태를 변경할 수 없습니다.");
        }
    }

    // 주문 취소
    @Transactional
    public void delete(Long orderId, DeleteOrderRequest request) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("존재하지 않는 주문입니다.")
        );
        if (order.getOrderStatus() == OrderStatus.CANCEL) {
            throw new OrderStatusNotAllowedException("이미 취소된 상품입니다.");
        } else if (order.getOrderStatus() != OrderStatus.PREPARE) {
            throw new OrderStatusNotAllowedException("주문 취소는 준비 상태에만 가능합니다.");
        }
        order.canceledOrder(request.canceledReason);
        order.updateOrderStatus(OrderStatus.CANCEL);
        order.getCustomer().cancelOrder(order.getAmount());
        order.getProduct().updateStock(order.getProduct().getStock() + order.getQuantity());
        // 주문 수량은 1 이상 -> 단종, 판매중은 유지, 품절은 상태 변경
        if (order.getProduct().getStatus() == ProductStatus.SOLD_OUT) {
            order.getProduct().changeStatus(ProductStatus.ON_SALE);
        }
        // 주문은 삭제되도 데이터 조회 가능
        orderRepository.deleteById(orderId);
    }
}
