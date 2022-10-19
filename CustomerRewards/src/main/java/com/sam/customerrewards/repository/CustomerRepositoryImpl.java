package com.sam.customerrewards.repository;

import com.sam.customerrewards.pojo.Customer;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final Map<String, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public Customer getCustomer(String id) {
        return customers.get(id);
    }

    @Override
    public void addCustomer(Customer customer) {
        String id = customer.getId();
        customers.put(id, customer);
    }

    @Override
    public void deleteCustomer(String id) {
        customers.remove(id);
    }

    @PostConstruct
    private void initData() {
        Customer customer1 = new Customer();
        customer1.getRewards().addRewards("january", 2022, 50);
        customer1.getRewards().addRewards("february", 2022,50);
        customer1.getRewards().addRewards("march", 2022,100);
        customer1.setId("1");
        addCustomer(customer1);

        Customer customer2 = new Customer();
        customer2.getRewards().addRewards("april", 2022,50);
        customer2.getRewards().addRewards("july", 2022,50);
        customer2.getRewards().addRewards("december", 2022,100);
        customer2.setId("2");
        addCustomer(customer2);

        Customer customer3 = new Customer();
        customer3.getRewards().addRewards("march", 2022,50);
        customer3.getRewards().addRewards("june", 2022,50);
        customer3.getRewards().addRewards("october", 2022,100);
        customer3.setId("3");
        addCustomer(customer3);
    }
}
