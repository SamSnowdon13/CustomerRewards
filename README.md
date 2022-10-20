# Spring Boot "Customer Rewards" WebAPI Development

This is a sample Java / Maven / Spring Boot application thats goal is to find the amount of rewards customers have form their total purchases.
  
# About the Service

This service is a simple customer REST service. It uses CustomerRepository as my custom data set to best represent my solution wihtout the need for an actual database. Autowiring this into my CustomerServiceImpl class I am able to provide all the custom logic that is needed to send back the correct data once we reach the endpoints.  
All methods in the service include:
-addCustomerRewards(List<AddRewardsRequest> addRewardsRequests) which passes in a List of AddRewardsRequests. This then integrates the logic needed to calculate the correct amount of pointes needed based on the customers total purchases.
  
-getCustomers which simply returns a list of all the customers
  
-getCustomer(String id) uses an id passed in as a parameter so that we can return just a single requested customer
  
-addCustomer(String id) adds a customer based on the passed in id that can later be modified through a PATCH endpoint.
  
-deleteCustomer(String id) delets a customer based on the id provided.

This Service also includes 2 POJOs as well as a AddRewardsRequeust that has validation that no fields are null and the month is valid.

###### Rewards
This includes a concurrent HashMap to apply some thread safety, and the following methods 
-getTotalRewards() a simple stream to calculate the sum of the monthly rewards and return them.
-getRewardsByDate(String key) that returns the value of the monthlyRewards Map based on the passed in Key
-addRewards(String month, Integer year, Integer rewards) add the rewards to our monthlyRewards HashMap with the passed in year, month, and rewards.

###### Customer
Simple POJO that has three fields. A String id, and a Rewards object.
-getTotalRewards() returns the total amount of rewards necesarry and uses @JsonProperty to let Jackson know to map the JSON property name to the result of the Java method.

# CustomerController/Endpoints
There are five endpoints inside of the Customer Controller which when necessary test simple edge cases and have logging so that we can understand what went wrong and where. 

Here are the examples of the endpoints that can be reached.

```
GET localhost:8080/customer  -- Will return a list of all the customers
[
    {
        "id": "1",
        "rewards": {
            "monthlyRewards": {
                "2022-march": 100,
                "2022-january": 50,
                "2022-february": 50
            }
        },
        "totalRewards": 200
    },
    {
        "id": "2",
        "rewards": {
            "monthlyRewards": {
                "2022-april": 50,
                "2022-december": 100,
                "2022-july": 50
            }
        },
        "totalRewards": 200
    },
    {
        "id": "3",
        "rewards": {
            "monthlyRewards": {
                "2022-december": 250
            }
        },
        "totalRewards": 250
    }
]
```

```
GET localhost:8080/customer/1   returns information on selected customer
{
    "id": "1",
    "rewards": {
        "monthlyRewards": {
            "2022-march": 100,
            "2022-january": 50,
            "2022-february": 50
        }
    },
    "totalRewards": 200
}

```

```
PUT localhost:8080/customer/4  Will add a new customer with selected Id(Still need to fill in transaction info with PATCH)
{
    "id": "4",
    "rewards": {
        "monthlyRewards": {}
    },
    "totalRewards": 0
}
```
```
PATCH localhost:8080/customer

Pass in RequestBody
[
    {
        "id": "4",
        "month": "January",
        "year": 2022,
        "purchaseAmount": 200
    }
]

JSON returned:
[
    {
        "id": "4",
        "rewards": {
            "monthlyRewards": {
                "2022-january": 250
            }
        },
        "totalRewards": 250
    }
]
```

# Tests
There are also tests covering all of the basic needs of the application for the repository and service using JUnit and mockito
###### Repository Tests 

Simple tests to see if the repository properly initializes with the correct number of customers, if we can properly add a customer, and final if we can properly delete a customer.

###### Service Tests

Tests covering whether we can truly return a collection of all our customers, if we can return a customer just through id, and finally making sure that we can add our customer rewards.
