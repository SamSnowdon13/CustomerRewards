package com.sam.customerrewards.repository;

import com.sam.customerrewards.pojo.Customer;

import java.util.Map;

public interface CustomerRepository {

    Map<String, Customer> getCustomers();

    Customer getCustomer(String id);

    void addCustomer(Customer customer);

    void deleteCustomer(String id);
}
