package com.sam.customerrewards.service;

import com.sam.customerrewards.pojo.Customer;
import com.sam.customerrewards.pojo.request.AddRewardsRequest;
import com.sam.customerrewards.repository.CustomerRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    CustomerRepositoryImpl customerRepository = Mockito.mock(CustomerRepositoryImpl.class);
    CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository);

    @Test
    @DisplayName("getCustomers returns collection of all customers")
    void scenario1() {
        Map<String, Customer> customerMap = getTestCustomerMap();

        when(customerRepository.getCustomers()).thenReturn(customerMap);

        Collection<Customer> customers = customerService.getCustomers();
        assert customers.size() == 3;
        customerMap.values().forEach(c -> {
            assert customers.contains(c);
        });
    }

    @Test
    @DisplayName("getCustomer returns customer by id")
    void scenario2() {
        List<Customer> testCustomers = getTestCustomers();
        Customer testCustomer = testCustomers.get(0);

        when(customerRepository.getCustomer("test1")).thenReturn(testCustomer);

        Customer customer = customerService.getCustomer("test1");
        assert customer != null;
        assert customer.getId().equals("test1");
        assert customer.getRewards().getTotalRewards() == 0;
    }

    @Test
    @DisplayName("addCustomerRewards works as expected")
    void scenario3() {
        List<Customer> testCustomers =  getTestCustomers();

        when(customerRepository.getCustomer("test1")).thenReturn(testCustomers.get(0));
        when(customerRepository.getCustomer("test2")).thenReturn(testCustomers.get(1));

        AddRewardsRequest addRewardsRequest1 = new AddRewardsRequest();
        addRewardsRequest1.setId("test1");
        addRewardsRequest1.setMonth("january");
        addRewardsRequest1.setYear(2022);
        addRewardsRequest1.setPurchaseAmount(120);

        AddRewardsRequest addRewardsRequest2 = new AddRewardsRequest();
        addRewardsRequest2.setId("test2");
        addRewardsRequest2.setMonth("february");
        addRewardsRequest2.setYear(2021);
        addRewardsRequest2.setPurchaseAmount(200);

        List<AddRewardsRequest> requests = List.of(addRewardsRequest1, addRewardsRequest2);
        Set<Customer> customers = customerService.addCustomerRewards(requests);

        customers.forEach(c -> {
            if (Objects.equals(c.getId(), "test1")) {

                assert c.getId().equals("test1");
                assert c.getRewards().getMonthlyRewards().size() == 1;
                assert c.getRewards().getRewardsByDate("2022-january") == 90;
                assert c.getRewards().getTotalRewards() == 90;

            } else if (Objects.equals(c.getId(), "test2")) {

                assert c.getId().equals("test2");
                assert c.getRewards().getMonthlyRewards().size() == 1;
                assert c.getRewards().getRewardsByDate("2021-february") == 250;
                assert c.getRewards().getTotalRewards() == 250;

            }
        });
    }

    private Map<String, Customer> getTestCustomerMap() {
        Customer customer1 = new Customer();
        customer1.setId("1");
        Customer customer2 = new Customer();
        customer1.setId("2");
        Customer customer3 = new Customer();
        customer1.setId("3");

        Map<String, Customer> customerMap = new HashMap<>();
        customerMap.put("1", customer1);
        customerMap.put("2", customer2);
        customerMap.put("3", customer3);

        return customerMap;
    }

    private List<Customer> getTestCustomers() {
        Customer customer1 = new Customer();
        customer1.setId("test1");
        Customer customer2 = new Customer();
        customer2.setId("test2");

        return List.of(customer1, customer2);
    }
}
