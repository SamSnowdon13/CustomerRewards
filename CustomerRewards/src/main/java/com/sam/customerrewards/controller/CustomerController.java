package com.sam.customerrewards.controller;

import com.sam.customerrewards.pojo.Customer;
import com.sam.customerrewards.pojo.request.AddRewardsRequest;
import com.sam.customerrewards.service.CustomerServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customer")
@Log4j2
public class CustomerController {

    private final CustomerServiceImpl customerService;
    @Value("#{${validMonths}}")
    Set<String> validMonths;

    @Autowired
    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public ResponseEntity<Collection<Customer>> getCustomers() {
        Collection<Customer> customers = customerService.getCustomers();
        return ResponseEntity.ok().body(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String id) {
        Customer customer = customerService.getCustomer(id);

        if (customer == null) {
            log.info("Customer not found with id={}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> addCustomer(@PathVariable String id) {
        Customer customer = customerService.addCustomer(id);
        log.info("Customer added with id={}", id);
        return ResponseEntity.ok().body(customer);
    }

    @PatchMapping()
    public ResponseEntity<Set<Customer>> addRewards(@RequestBody List<AddRewardsRequest> addRewardsRequests) {
        for (AddRewardsRequest addRewardsRequest : addRewardsRequests) {
            if (!addRewardsRequest.isValidRequest()) {
                log.info("Invalid addRewardsRequest received");
                return ResponseEntity.badRequest().build();
            }
            else if (!validMonths.contains(addRewardsRequest.getMonth())) {
                log.info("Invalid month received in addRewardsRequest");
                return ResponseEntity.badRequest().build();
            }
        }

        Set<Customer> customers = customerService.addCustomerRewards(addRewardsRequests);
        return ResponseEntity.ok().body(customers);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        log.info("customer deleted with id={}", id);
        return ResponseEntity.ok().build();
    }
}
