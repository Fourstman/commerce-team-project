package com.commerceteamproject.customer.controller;

import com.commerceteamproject.customer.dto.*;
import com.commerceteamproject.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/customers")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // TODO
    // 고객 리스트 조회
    @GetMapping
    public ResponseEntity<Page<GetCustomerListResponse>> findAll() {
        return null;
    }

    // 고객 상세 조회
    @GetMapping("/{customerId}")
    public ResponseEntity<GetCustomerResponse> findOne(@PathVariable Long customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findOne(customerId));
    }

    // 고객 정보 수정
    @PatchMapping("/{customerId}/info")
    public ResponseEntity<UpdateCustomerInformationResponse> updateInformation(
            @PathVariable Long customerId, @Valid @RequestBody UpdateCustomerInformationRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateInformation(customerId, request));
    }

    // 고객 상태 변경
    @PatchMapping("/{customerId}/state")
    public ResponseEntity<UpdateCustomerStateResponse> updateState(
            @PathVariable Long customerId, @Valid @RequestBody UpdateCustomerStateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateState(customerId, request));
    }
    
    // 고객 삭제
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
