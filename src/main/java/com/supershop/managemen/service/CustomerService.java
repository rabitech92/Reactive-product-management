package com.supershop.managemen.service;

import com.supershop.managemen.dto.CustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Flux<CustomerDto> getAllCustomer();
    Mono<CustomerDto> saveCustomer(CustomerDto customer);
    Mono<CustomerDto> getCustomerById(String id);
    Mono<Void> deleteCustomer(String id);
    Mono<CustomerDto> updateCustomer(String id, CustomerDto customer);
}
