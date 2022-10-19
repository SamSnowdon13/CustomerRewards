package com.sam.customerrewards.repository;

import com.sam.customerrewards.pojo.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomerRepositoryTest {

    CustomerRepositoryImpl customerRepository = new CustomerRepositoryImpl();

    @BeforeEach
    public void init() {
        Customer customer1 = new Customer();
        customer1.getRewards().addRewards("january", 2022, 50);
        customer1.getRewards().addRewards("february", 2022,50);
        customer1.getRewards().addRewards("march", 2022,100);
        customer1.setId("1");
        customerRepository.addCustomer(customer1);

        Customer customer2 = new Customer();
        customer2.getRewards().addRewards("april", 2022,50);
        customer2.getRewards().addRewards("july", 2022,50);
        customer2.getRewards().addRewards("december", 2022,100);
        customer2.setId("2");
        customerRepository.addCustomer(customer2);

        Customer customer3 = new Customer();
        customer3.getRewards().addRewards("march", 2022,50);
        customer3.getRewards().addRewards("june", 2022,50);
        customer3.getRewards().addRewards("october", 2022,100);
        customer3.setId("3");
        customerRepository.addCustomer(customer3);
    }

    @Test
    @DisplayName("Repository initializes with 3 customers")
    void initTest() {
        Customer customer1 = customerRepository.getCustomer("1");
        Customer customer2 = customerRepository.getCustomer("2");
        Customer customer3 = customerRepository.getCustomer("3");

        assert customerRepository.getCustomers().size() == 3;
        assert customer1 != null;
        assert customer2 != null;
        assert customer3 != null;
        assert customer1.getRewards().getTotalRewards() == 200;
        assert customer2.getRewards().getTotalRewards() == 200;
        assert customer3.getRewards().getTotalRewards() == 200;
    }

    @Test
    @DisplayName("Add customer")
    void scenario1() {
        Customer newCustomer = new Customer();
        newCustomer.setId("new customer");
        customerRepository.addCustomer(newCustomer);

        Customer customer = customerRepository.getCustomer("new customer");
        assert customer.getId().equals("new customer");
        assert customer.getRewards() != null;
        assert customer.getRewards().getTotalRewards() == 0;
        assert customerRepository.getCustomers().size() == 4;
    }

    @Test
    @DisplayName("Delete customer")
    void scenario2() {
        customerRepository.deleteCustomer("1");
        customerRepository.deleteCustomer("2");
        customerRepository.deleteCustomer("3");

        assert customerRepository.getCustomer("1") == null;
        assert customerRepository.getCustomer("2") == null;
        assert customerRepository.getCustomer("3") == null;
        assert customerRepository.getCustomers().size() == 0;
    }
}
