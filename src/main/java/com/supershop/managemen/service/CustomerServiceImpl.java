package com.supershop.managemen.service;

import com.supershop.managemen.dto.CustomerDto;
import com.supershop.managemen.entity.Customer;
import com.supershop.managemen.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Flux<CustomerDto> getAllCustomer() {
        return customerRepository.findAll()
                .map(customer -> new CustomerDto()); // Map each Customer to CustomerDto
    }

    @Override
    public Mono<CustomerDto> saveCustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto,Customer.class);
        return customerRepository
                .save(customer)
                .map(response -> modelMapper.map(response,CustomerDto.class));
    }

    @Override
    public Mono<CustomerDto> getCustomerById(String id) {
        return customerRepository
                .findById(id)
                .map(customer -> modelMapper.map(customer,CustomerDto.class))
                .switchIfEmpty(
Mono.error(new NoSuchElementException("Customer not found with id: " + id)));

    }

    @Override
    public Mono<Void> deleteCustomer(String id) {
        return customerRepository
                .deleteById(id)
                .switchIfEmpty(
                        Mono.error(new NoSuchElementException("Customer not found with id: " + id)))
                                .flatMap(customer -> customerRepository.deleteById(id));
    }

    @Override
    public Mono<CustomerDto> updateCustomer(String id, CustomerDto customerDto) {
        return customerRepository
                .findById(id)
                .flatMap(
                        existingCustomer -> {
                            existingCustomer.setFirstName(customerDto.getFirstName());
                            existingCustomer.setLastName(customerDto.getLastName());
                            existingCustomer.setEmail(customerDto.getEmail());
                            existingCustomer.setPhoneNumber(customerDto.getPhoneNumber());
                            existingCustomer.setAddress(customerDto.getAddress());
                return customerRepository.save(existingCustomer);
                        })
                .map(updateCustomer -> modelMapper.map(updateCustomer, CustomerDto.class))
                .switchIfEmpty(Mono.error(new NoSuchElementException("Customer not found with id: " + id)));
    }
}
