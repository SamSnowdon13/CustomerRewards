package com.sam.customerrewards.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Customer {

    private String id;
    private Rewards rewards = new Rewards();

    @JsonProperty("totalRewards")
    private Integer getTotalRewards() {
        return rewards.getTotalRewards();
    }
}
