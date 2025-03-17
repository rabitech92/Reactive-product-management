package com.supershop.managemen.controller;

import com.supershop.managemen.dto.CustomerDto;
import com.supershop.managemen.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/api/customers")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public Flux<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomer();
    }

    @PostMapping
    public Mono<ResponseEntity<CustomerDto>> addCustomer(@RequestBody(required = false) CustomerDto customerDto) {
        if (customerDto == null) {
            return Mono.just(ResponseEntity.badRequest().body(null)); // Return 400 Bad Request
        }

        return customerService
                .saveCustomer(customerDto)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response))
                .defaultIfEmpty(ResponseEntity.badRequest().build()); // Handle empty response
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerDto>> getCustomerById(@PathVariable String id) {
        return customerService
                .getCustomerById(id)
                .map(response -> ResponseEntity.status(HttpStatus.ACCEPTED).body(response))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null))); // You can replace 'null' with a custom error message DTO
    }


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteCustomer(@PathVariable String id) {
        return customerService
                .deleteCustomer(id)
                .then(Mono.fromRunnable(() -> System.out.println("Customer with ID " + id + " has been deleted")))
                .then(Mono.just(ResponseEntity.ok("Customer with ID " + id + " successfully deleted")));
    }



    @PatchMapping("/{id}")
    public Mono<ResponseEntity<CustomerDto>> updateCustomer(@PathVariable String id, @RequestBody CustomerDto customerDto) {
    return customerService
            .updateCustomer(id, customerDto)
            .map(ResponseEntity::ok)
            .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }


}
