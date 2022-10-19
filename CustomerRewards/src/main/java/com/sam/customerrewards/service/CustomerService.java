package com.sam.customerrewards.service;

import com.sam.customerrewards.pojo.Customer;
import com.sam.customerrewards.pojo.request.AddRewardsRequest;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface CustomerService {

    Set<Customer> addCustomerRewards(List<AddRewardsRequest> addRewardsRequests);

    Collection<Customer> getCustomers();

    Customer getCustomer(String id);

    Customer addCustomer(String id);

    void deleteCustomer(String id);
}
