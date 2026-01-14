package com.commerceteamproject.customer.service;

import com.commerceteamproject.customer.dto.*;
import com.commerceteamproject.customer.entity.Customer;
import com.commerceteamproject.customer.entity.CustomerSortBy;
import com.commerceteamproject.customer.entity.CustomerSortOrder;
import com.commerceteamproject.customer.entity.CustomerState;
import com.commerceteamproject.customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public PageResponse<GetCustomerListResponse> findAll(String keyword, int pageNum, int pageSize, CustomerSortBy sortBy, CustomerSortOrder sortOrder, CustomerState state) {

        // 정렬 기준이 가입일이면 내림차순 정렬, 그 외엔 오름차순 정렬
        if (sortOrder == null) {
            if (sortBy == CustomerSortBy.createdAt) {
                sortOrder = CustomerSortOrder.desc;
            } else {
                sortOrder = CustomerSortOrder.asc;
            }
        }

        // 정렬 및 페이징 조건 설정
        Sort.Direction direction = Sort.Direction.fromString(sortOrder.name());
        Sort sort = Sort.by(direction, sortBy.name());
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        // 키워드가 있으면 키워드가 포함된 이름/이메일 검색
        // 상태가 있으면 상태 기준으로 필터링
        // 키워드/상태가 없으면 전체 조회
        Page<Customer> customers = customerRepository.findByKeywordAndState(keyword, state, pageable);

        Page<GetCustomerListResponse> page = customers.map(customer -> new GetCustomerListResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getState().getDescription(),
                customer.getCreatedAt()
        ));
        return new PageResponse<>(page);
    }

    // 고객 상세 조회
    @Transactional(readOnly = true)
    public GetCustomerResponse findOne(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
        );
        return new GetCustomerResponse(
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getState().getDescription(),
                customer.getCreatedAt()
        );
    }

    // 고객 정보 수정
    @Transactional
    public UpdateCustomerInformationResponse updateInformation(Long customerId, @Valid UpdateCustomerInformationRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
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
    public UpdateCustomerStateResponse updateState(Long customerId, @Valid UpdateCustomerStateRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
        );
        customer.updateState(request);
        return new UpdateCustomerStateResponse(
                customer.getName(),
                customer.getState().getDescription()
        );
    }

    // 고객 삭제
    @Transactional
    public void delete(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
        );
        customerRepository.deleteById(customerId);
    }
}
