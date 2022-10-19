package com.sam.customerrewards.service;

import com.sam.customerrewards.pojo.Customer;
import com.sam.customerrewards.pojo.Rewards;
import com.sam.customerrewards.pojo.request.AddRewardsRequest;
import com.sam.customerrewards.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Set<Customer> addCustomerRewards(List<AddRewardsRequest> addRewardsRequests) {
        Set<Customer> customers = new HashSet<>();

        addRewardsRequests.forEach(request -> {
            String id = request.getId();
            Integer purchaseAmount = request.getPurchaseAmount();
            String month = request.getMonth();
            Integer year = request.getYear();

            Customer customer = customerRepository.getCustomer(id);
            if (customer != null) {
                int rewardAmount = 0;
                if (purchaseAmount > 100) {
                    int amountOverOneHundred = purchaseAmount - 100;
                    rewardAmount += amountOverOneHundred * 2 + 50;
                } else if (purchaseAmount > 50) {
                    int amountOverFifty = purchaseAmount - 50;
                    rewardAmount += amountOverFifty;
                }

                Rewards customerRewards = customer.getRewards();
                customerRewards.addRewards(month, year, rewardAmount);

                customers.add(customer);
            }
        });

        return customers;
    }

    @Override
    public Collection<Customer> getCustomers() {
        return customerRepository.getCustomers().values();
    }

    @Override
    public Customer getCustomer(String id) {
        return customerRepository.getCustomer(id);
    }

    @Override
    public Customer addCustomer(String id) {
        Customer customer = new Customer();
        customer.setId(id);
        customerRepository.addCustomer(customer);
        return customer;
    }

    @Override
    public void deleteCustomer(String id) {
        customerRepository.deleteCustomer(id);
    }
}
