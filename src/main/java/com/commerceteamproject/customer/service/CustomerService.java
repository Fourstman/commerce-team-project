package com.commerceteamproject.customer.service;

import com.commerceteamproject.common.dto.PageResponse;
import com.commerceteamproject.customer.dto.*;
import com.commerceteamproject.customer.entity.Customer;
import com.commerceteamproject.customer.entity.CustomerSortBy;
import com.commerceteamproject.customer.entity.CustomerStatus;
import com.commerceteamproject.customer.exception.CustomerNotFoundException;
import com.commerceteamproject.common.exception.InvalidParameterException;
import com.commerceteamproject.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    // 고객 리스트 조회
    @Transactional(readOnly = true)
    public PageResponse<GetCustomerListResponse> findAll(String keyword, CustomerStatus status, Pageable pageable) {

        // pageable sort 검증
        Sort.Order order = pageable.getSort().iterator().next();
        String property = order.getProperty();
        if (!CustomerSortBy.exists(property)) {
            throw new InvalidParameterException("잘못된 정렬 기준입니다.");
        }

        // 키워드가 있으면 키워드가 포함된 이름/이메일 필터링
        // 상태가 있으면 상태 기준으로 필터링
        // 키워드/상태가 없으면 전체 조회
        Page<Customer> customers = customerRepository.findByKeywordAndStatus(keyword, status, pageable);

        Page<GetCustomerListResponse> page = customers.map(customer -> new GetCustomerListResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getTotalOrderCount(),
                customer.getTotalOrderAmount(),
                customer.getCreatedAt()
        ));
        return new PageResponse<>(page);
    }

    // 고객 상세 조회
    @Transactional(readOnly = true)
    public GetCustomerResponse findOne(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("존재하지 않는 고객입니다.")
        );
        return new GetCustomerResponse(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getStatus(),
                customer.getTotalOrderCount(),
                customer.getTotalOrderAmount(),
                customer.getCreatedAt()
        );
    }

    // 고객 정보 수정
    @Transactional
    public UpdateCustomerInformationResponse updateInformation(Long customerId, @Valid UpdateCustomerInformationRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("존재하지 않는 고객입니다.")
        );
        customer.updateInformation(request);
        return new UpdateCustomerInformationResponse(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone()
        );
    }

    // 고객 상태 변경
    @Transactional
    public UpdateCustomerStatusResponse updateStatus(Long customerId, @Valid UpdateCustomerStatusRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("존재하지 않는 고객입니다.")
        );
        customer.updateStatus(request);
        return new UpdateCustomerStatusResponse(
                customer.getName(),
                customer.getStatus()
        );
    }

    // 고객 삭제
    @Transactional
    public void delete(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("존재하지 않는 고객입니다.")
        );
        customerRepository.deleteById(customerId);
    }
}
