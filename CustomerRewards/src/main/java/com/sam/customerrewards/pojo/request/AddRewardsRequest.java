package com.sam.customerrewards.pojo.request;

import lombok.Data;

@Data
public class AddRewardsRequest {

    private String id;
    private String month;
    private Integer year;
    private Integer purchaseAmount;

    public Boolean isValidRequest() {
        return id != null
                && month != null
                && year != null
                && purchaseAmount != null;
    }

    public String getMonth() {
        return month.toLowerCase();
    }
}
